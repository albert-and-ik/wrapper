package com.example.wrapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;


public final class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream output;
    private final FilterServletOutputStream filterOutput;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
        filterOutput = new FilterServletOutputStream(output);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return filterOutput;
    }

    public byte[] getDataStream() {
        return output.toByteArray();
    }
}