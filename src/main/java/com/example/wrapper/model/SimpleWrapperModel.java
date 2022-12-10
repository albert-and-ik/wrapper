package com.example.wrapper.model;

public class SimpleWrapperModel<T> extends AbstractWrapperModel<T> {
    private T data;

    @Override
    public void setResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
