package com.example.wrapper.anotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Wrap
@RestController
public @interface WrapRestController {
}

