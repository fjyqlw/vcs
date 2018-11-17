package com.lw.vcs.auth.service;

import com.lw.vcs.audit.model.Audit;
import com.lw.vcs.audit.model.AuditUser;
import com.lw.vcs.audit.service.AuditService;
import com.lw.vcs.auth.mapper.UserDao;
import com.lw.vcs.auth.model.LoginVO;
import com.lw.vcs.auth.model.RegisterVO;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.auth.model.UserVO;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.redis.RedisService;
import com.lw.vcs.redis.SvnUserKey;
import com.lw.vcs.redis.UserKey;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import com.lw.vcs.svnkit.service.SvnOptService;
import com.lw.vcs.utils.MD5Util;
import com.lw.vcs.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/8/7 22:12
 * @Description：
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuditService auditService;

    public User getById(String userId) {
       return userDao.getById(userId);
    }
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<User> list() {
        return userDao.list();
    }

    public List<User> list(Integer state) {
        return userDao.listByState(state);
    }


    /**
     * 用户
     * @return
     */
    public void password(String userId,String password,String salt) {
        userDao.password(userId,password,salt);
    }

    /**
     * 审核用户
     * @param userId
     * @param state
     * @return
     */
    public void audit(String userId,Integer state,AuditUser auditUser) {
        String code = UUIDUtil.getUUID();
        auditUser.setCode(code);
        userDao.audit(userId,state);
        auditService.audit(auditUser);
    }

    @Transactional
    public void save(RegisterVO registerVO) {
        userDao.register(registerVO);
        AuditUser auditUser = AuditUser.create();
        auditUser.setAuditId(UUIDUtil.getUUID())
                .setApplyUserId(registerVO.getUserId())
                .setComment(registerVO.getComment())
                .setCreateTime(sdf.format(new Date()));
        auditService.createUserAudit(auditUser);
    }

    @Transactional
    public void active(String userId, String password, String salt, String auditId) {
        userDao.password(userId,password,salt);
        auditService.active(auditId);
    }
}
