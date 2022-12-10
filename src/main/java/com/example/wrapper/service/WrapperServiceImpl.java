package com.example.wrapper.service;

import com.example.wrapper.model.AbstractWrapperModel;

import java.lang.reflect.Constructor;

public class WrapperServiceImpl implements WrapperService {

    private final Class<? extends AbstractWrapperModel<?>> wrapperModel;

    @SuppressWarnings("unchecked")
    public WrapperServiceImpl(AbstractWrapperModel<?> wrapperModel) {
        this.wrapperModel = (Class<AbstractWrapperModel<?>>) wrapperModel.getClass();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> AbstractWrapperModel<T> getWrapperModel(T data) {
        AbstractWrapperModel<T> model;

        try {
            Constructor<?> ctor  = wrapperModel.getDeclaredConstructor();
            model = (AbstractWrapperModel<T>) ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.setResponse(data);
        return model;
    }
}
