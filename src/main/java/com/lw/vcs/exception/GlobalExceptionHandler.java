package com.lw.vcs.exception;

import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


/**
 * @Author：lian.wei
 * @Date：2018/8/10 21:25
 * @Description：全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e){
        logger.error(e.toString());
        if(e instanceof BindException){
            BindException ex=(BindException)e;

            List<ObjectError> errors = ex.getAllErrors();

            StringBuilder sb = new StringBuilder();
            for (ObjectError oe : errors){
                sb.append(String.format("%s,",oe.getDefaultMessage()));
            }

            String msg = sb.toString().substring(0,sb.toString().length()-1);
            return Result.error(CodeMsg.BIND_ERROR.fileArgs(msg));
        }else if(e instanceof GlobalExceptopn){
            GlobalExceptopn ex = (GlobalExceptopn)e;
            CodeMsg cm = ex.getCm();

            return Result.error(cm);
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
