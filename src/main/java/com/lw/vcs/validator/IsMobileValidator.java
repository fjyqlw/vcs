package com.lw.vcs.validator;

import com.lw.vcs.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author：lian.wei
 * @Date：2018/8/10 21:09
 * @Description：手机号验证器
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
   private boolean required = false;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            if(StringUtils.isEmpty(s)){
                return false;
            }
        }else {
            if(StringUtils.isEmpty(s)){
                return true;
            }
        }

        if(ValidatorUtil.isMobile(s)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }
}
