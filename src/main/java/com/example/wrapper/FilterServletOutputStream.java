package com.example.wrapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public final class FilterServletOutputStream extends ServletOutputStream {

    private final DataOutputStream output;

    public FilterServletOutputStream(OutputStream output) {
        this.output = new DataOutputStream(output);
    }

    @Override
    public void write(int arg0) throws IOException {
        output.write(arg0);
    }

    @Override
    public void write(byte[] arg0, int arg1, int arg2) throws IOException {
        output.write(arg0, arg1, arg2);
    }

    @Override
    public void write(byte[] arg0) throws IOException {
        output.write(arg0);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }
}