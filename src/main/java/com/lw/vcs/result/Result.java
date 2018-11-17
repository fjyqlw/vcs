package com.lw.vcs.result;

/**
 * @Author：lian.wei
 * @Date：2018/8/7 21:03
 * @Description：
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }
    private Result() {
        this.code = 0;
        this.msg = "success";
    }

    private Result(CodeMsg cm) {
        if(cm == null){
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }
    /**
     * 调用成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }
    /**
     * 调用成功
     * @return
     */
    public static <T> Result<T> success(){
        return new Result<T>();
    }


    /**
     * 调用失败
     * @param cm
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
