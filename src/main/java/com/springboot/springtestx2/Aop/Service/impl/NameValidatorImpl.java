package com.springboot.springtestx2.Aop.Service.impl;

import com.springboot.springtestx2.Aop.Service.NameValidator;
import org.springframework.stereotype.Service;

@Service
public class NameValidatorImpl implements NameValidator {
    @Override
    public boolean validate(String name) {
        System.out.println("增强功能 " + NameValidator.class.getSimpleName());
        return name != null;
    }
}
