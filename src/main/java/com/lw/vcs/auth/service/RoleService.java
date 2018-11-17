package com.lw.vcs.auth.service;

import com.lw.vcs.auth.mapper.RoleDao;
import com.lw.vcs.auth.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/27 23:22
 * @Description：
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public List<Role> list() {
        return roleDao.list();
    }

    public boolean insert(Role role) {
        return roleDao.insert(role) > 0;
    }

    public boolean deleteById(String roleId) {
        return roleDao.deleteById(roleId) > 0;
    }

    public Role getById(String roleId) {
        return roleDao.getById(roleId);
    }
}
