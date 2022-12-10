package com.example.wrapper;

import com.example.wrapper.model.AbstractWrapperModel;
import com.example.wrapper.model.SimpleWrapperModel;
import com.example.wrapper.service.WrapperService;
import com.example.wrapper.service.WrapperServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Configuration
public class WrapperConfig {

    @Bean
    public ResponseFilter responseFilter(ApplicationContext context, @Nullable AbstractWrapperModel<?> model) {
        return new ResponseFilter(context, wrapperService(model));
    }


    @Bean
    public WrapperService wrapperService(@Nullable AbstractWrapperModel<?> model) {
        return new WrapperServiceImpl(Objects.requireNonNullElseGet(model, SimpleWrapperModel::new));
    }
}
