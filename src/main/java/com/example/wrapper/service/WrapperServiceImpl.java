package com.example.wrapper.service;

import com.example.wrapper.model.AbstractWrapperModel;

import java.lang.reflect.Constructor;

public class WrapperServiceImpl implements WrapperService {

    private final Constructor<? extends AbstractWrapperModel<?>> ctor;

    @SuppressWarnings("unchecked")
    public WrapperServiceImpl(AbstractWrapperModel<?> wrapperModel) {
        try {
            this.ctor = (Constructor<? extends AbstractWrapperModel<?>>) wrapperModel.getClass().getDeclaredConstructor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> AbstractWrapperModel<T> getWrapperModel(T data) {
        AbstractWrapperModel<T> model = null;

        try {
            model = (AbstractWrapperModel<T>) ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.setResponse(data);
        return model;
    }
}
