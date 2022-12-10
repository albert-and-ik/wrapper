package com.example.wrapper.model;

import com.example.wrapper.anotation.NeverWrap;

@NeverWrap
public abstract class AbstractWrapperModel<T> {
    public abstract void setResponse(T data);
}
