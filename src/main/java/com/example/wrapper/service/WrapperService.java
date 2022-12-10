package com.example.wrapper.service;

import com.example.wrapper.model.AbstractWrapperModel;


public interface WrapperService {
    <T> AbstractWrapperModel<T> getWrapperModel(T data);
}
