package com.lw.vcs.audit.service;

import com.lw.vcs.audit.mapper.AuditUserDao;
import com.lw.vcs.audit.model.AuditDetail;
import com.lw.vcs.audit.model.AuditUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/10/7 20:35
 * @Description：
 */
@Service
public class AuditService {
    @Autowired
    private AuditUserDao auditUserDao;

    public void createUserAudit(AuditUser auditUser) {
        auditUserDao.insert(auditUser);
    }

    public AuditUser getById(String auditId) {
        return auditUserDao.getById(auditId);
    }

    public void audit(AuditUser auditUser) {
        auditUserDao.audit(auditUser);
    }

    public AuditUser validate(String auditId, String code) {
        return auditUserDao.validate(auditId,code);
    }

    public void active(String auditId) {
        auditUserDao.active(auditId);
    }
    public List<AuditDetail> list(Integer state){
        return auditUserDao.list(state);
    }
}
