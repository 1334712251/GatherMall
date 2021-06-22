package com.gathermall.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<isValue,Integer> {

    private Set<Integer> set= new HashSet();

    //初始化方法
    @Override
    public void initialize(isValue constraintAnnotation) {
        int[] value = constraintAnnotation.value();
        for (int item : value) {
            set.add(item);
        }
    }

    //判断是否校验成功
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
