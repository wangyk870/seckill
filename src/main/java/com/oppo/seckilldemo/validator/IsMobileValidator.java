package com.oppo.seckilldemo.validator;

import com.oppo.seckilldemo.annotation.IsMobile;
import com.oppo.seckilldemo.utils.StringUtil;
import com.oppo.seckilldemo.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(s);
        } else {
            if(StringUtil.isEmpty(s)){
                return true;
            } else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
