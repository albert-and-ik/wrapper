package com.example.wrapper;

import com.example.wrapper.anotation.*;
import com.example.wrapper.service.WrapperService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

public final class ResponseFilter implements Filter {

    private final ApplicationContext appContext;
    private final WrapperService service;
    private final ObjectMapper objectMapper;

    public ResponseFilter(ApplicationContext appContext, WrapperService service) {
        this.appContext = appContext;
        this.service = service;
        this.objectMapper = new ObjectMapper()
                .setSerializationInclusion(Include.NON_NULL);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        RequestMappingHandlerMapping req2HandlerMapping = (RequestMappingHandlerMapping) appContext.getBean("requestMappingHandlerMapping");
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

        HandlerExecutionChain handlerExecutionChain;

        try {
            handlerExecutionChain = req2HandlerMapping.getHandler((HttpServletRequest) request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        chain.doFilter(request, responseWrapper);

        byte[] responseBytes = responseWrapper.getDataStream();

        if(handlerExecutionChain != null) {

            HandlerMethod handlerMethod = (HandlerMethod) handlerExecutionChain.getHandler();

            if(needWrap(handlerMethod)) {
                Class<?> returnedType = handlerMethod.getMethod().getReturnType();

                Object data = returnedType.isAssignableFrom(String.class)
                        ? new String(responseBytes)
                        : objectMapper.readValue(responseBytes, returnedType);

                sendWithBaseResponse(response, data);
                return;
            }
        }

        response.getOutputStream().write(responseBytes);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    private <T> void sendWithBaseResponse(ServletResponse response, T data) throws IOException {

        byte[] bytesToSend = objectMapper.writeValueAsBytes(service.getWrapperModel(data));

        response.setContentLengthLong(bytesToSend.length);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getOutputStream().write(bytesToSend);
    }

    private static boolean needWrap(HandlerMethod handlerMethod) {
        Class<?> returnType = handlerMethod.getMethod().getReturnType();
        Class<?> beanType = handlerMethod.getBean().getClass();

        if(returnType.isAnnotationPresent(AlwaysWrap.class)) return true;

        if(returnType.isAssignableFrom(ResponseEntity.class)
                || returnType.isAnnotationPresent(NeverWrap.class)
                || handlerMethod.hasMethodAnnotation(NoWrap.class)
                || returnType.isAnnotationPresent(NoWrap.class)
                || beanType.isAnnotationPresent(NoWrap.class))
            return false;

        return beanType.isAnnotationPresent(WrapRestController.class)
                || beanType.isAnnotationPresent(Wrap.class)
                || handlerMethod.hasMethodAnnotation(Wrap.class);
    }
}