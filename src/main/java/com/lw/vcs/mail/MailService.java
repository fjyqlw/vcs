package com.lw.vcs.mail;

import com.lw.vcs.audit.model.AuditUser;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.utils.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：lian.wei
 * @Date：2018/10/12 21:04
 * @Description：
 */
@Service
public class MailService {
    @Autowired
    private MailSession mailSession;

    @Async
    public void sendRegisterAuditEmail(User user, AuditUser auditUser, HttpServletRequest request) {
        String code = auditUser.getCode();
        String host = "http://127.0.0.1:8000/#";
        String url = String.format("%s/email-validate/%s/%s",host,auditUser.getAuditId(),code);
        String content = ResourceUtil.read("mail/registerAuditPass.html");
        content = content.replace("${VCS.USERNAME}",user.getUserName());
        content = content.replace("${VCS.VALIDATE.URL}",url);
        MailData mailData = MailData.create()
                .setFrom("fjyqlw@foxmail.com")
                .setSubject("账号激活")
                .setContent(content)
                .addToList(user.getEmail());
        MailUtil.sendMail(mailData,mailSession);
    }

}
