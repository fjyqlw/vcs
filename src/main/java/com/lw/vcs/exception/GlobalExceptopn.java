package com.lw.vcs.exception;


import com.lw.vcs.result.CodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author：lian.wei
 * @Date：2018/8/10 22:29
 * @Description：
 */
public class GlobalExceptopn extends RuntimeException{

    private CodeMsg cm;

    /**
     *
     * @param cm
     */
    public GlobalExceptopn(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

    @Override
    public String toString() {
        return cm.toString();
    }
}
