package com.lw.vcs.auth.mapper;

import com.lw.vcs.auth.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/27 23:22
 * @Description：
 */
@Mapper
public interface RoleDao {
    @Select("SELECT * FROM T_BAS_ROLE")
     List<Role> list();

    @Select("SELECT * FROM T_BAS_ROLE WHERE ROLEID=#{roleId}")
     Role getById(@Param("roleId") String roleId);

    @Insert("INSERT INTO T_BAS_ROLE(ROLEID,ROLENAME) VALUES(#{param.roleId},#{param.roleName})")
     Integer insert(@Param("param") Role role);


    @Delete("DELETE T_BAS_ROLE WHERE ROLEID=#{roleId}")
    Integer deleteById(@Param("roleId") String roleId);
}
