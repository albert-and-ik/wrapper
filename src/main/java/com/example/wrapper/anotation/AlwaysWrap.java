package com.example.wrapper.anotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Wrap
@Inherited
public @interface AlwaysWrap {
}
