package com.lw.vcs.log;

import nl.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：lian.wei
 * @Date：2018/9/28 20:06
 * @Description：
 */
@Component
@Aspect
public class SystemLogAop {
    private Logger logger = LoggerFactory.getLogger(SystemLogAop.class);

    @After(("execution(* com.lw..*.*(..)) && @annotation(systemLog)"))
    public void doAfterAdvice(JoinPoint joinPoint, SystemLog systemLog) {

        String value = systemLog.value();
        LogType type = systemLog.type();
        boolean auth = systemLog.auth();

        //需要登录
        if (auth) {

        }

        switch (type) {
            case LOGIN:
                logger.info("执行登录");
                break;
            case LOGOUT:
                logger.info("执行退出");
                break;
            default:
                ;
        }
    }

    /**
     * <p>Discription:[根据request获取前台浏览器标识]</p>	*
     * Created on 2017年11月20日 下午7:30:08	*
     *
     * @param request request对象
     * @return String 浏览器标识
     */
    private static String getBrowserInfo(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String browserInfo = userAgent.getBrowser().toString();
        return browserInfo;
    }

}
