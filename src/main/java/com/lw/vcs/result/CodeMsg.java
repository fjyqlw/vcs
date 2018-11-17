package com.lw.vcs.result;

/**
 * @Author：lian.wei
 * @Date：2018/8/7 21:12
 * @Description：
 */
public class CodeMsg {
    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"seccuss");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101,"参数校验异常：%s");

    //用户异常
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500200,"手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500201,"手机号错误");
    public static CodeMsg MOBILE_NOT_FOUND = new CodeMsg(500202,"手机号不存在");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500203,"密码不能为空");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500204,"密码错误");
    public static CodeMsg AUTH_EXPIRE = new CodeMsg(500205,"身份过期");
    public static CodeMsg USER_NO_AUTH = new CodeMsg(500206,"用户未登录");
    public static CodeMsg USER_NOT_FOUND = new CodeMsg(500207,"用户不存在");
    public static CodeMsg USER_EXIST = new CodeMsg(500300,"用户已存在");
    public static CodeMsg USER_REGISTER_FAIL = new CodeMsg(500301,"用户注册失败");

    public static CodeMsg USER_NO_AUDIT = new CodeMsg(500400,"用户待审核");
    public static CodeMsg USER_FORBIDDEN = new CodeMsg(500401,"用户被禁用");
    public static CodeMsg USER_NO_ACTIVATED = new CodeMsg(500402,"账号未激活");


    //SVN异常
    public static CodeMsg SVN_NOT_WORK_COPY = new CodeMsg(600100,"不是有效的SVN工作目录");
    public static CodeMsg SVN_COMMIT_SUCCESS = new CodeMsg(600101,"提交成功");
    public static CodeMsg SVN_COMMIT_CONFLICT = new CodeMsg(600102,"提交失败，产生冲突");
    public static CodeMsg SVN_COMMIT_FILE_NOT_FOUND = new CodeMsg(600103,"提交失败，文件不存在");
    public static CodeMsg SVN_REPO_PATH_EXIST = new CodeMsg(600104,"资源库物理路径已存在");
    public static CodeMsg SVN_REPO_URL_EXIST = new CodeMsg(600105,"资源库URL路径已存在");
    public static CodeMsg SVN_REPO_URL_NOT_EXIST = new CodeMsg(600106,"资源库不存在");

    //SVN创建异常
    public static CodeMsg SVN_CREATE_URL_EXIST = new CodeMsg(600200,"URL已存在");

    //文件上传
    public static CodeMsg SVN_FILE_UPLOAD_ERROR = new CodeMsg(700100,"文件上传失败");


    public static CodeMsg AUDIT_ID_NO_FOUND = new CodeMsg(800100,"申请记录不存在");
    public static CodeMsg AUDIT_USER_FAILD = new CodeMsg(800101,"审核用户失败");
    public static CodeMsg AUDIT_URL_ERROR = new CodeMsg(800102,"链接已失效");

    //角色

    public static CodeMsg ROLE_NOT_FOUND = new CodeMsg(900100,"角色不存在");

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CodeMsg fileArgs(Object ... args){
        int code = this.code;
        String message = String.format(this.msg,args);

        return new CodeMsg(code,message);
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
