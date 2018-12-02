package com.lw.vcs.audit.mapper;

import com.lw.vcs.audit.model.Audit;
import com.lw.vcs.audit.model.AuditDetail;
import com.lw.vcs.audit.model.AuditUser;
import com.lw.vcs.auth.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/10/7 20:35
 * @Description：
 */
@Mapper
public interface AuditUserDao {
    @Insert("INSERT INTO T_BS_AUDIT_USER(\n" +
            "AUDITID,APPLYUSERID,CREATETIME,COMMENT)\n" +
            "VALUES(#{param.auditId},#{param.applyUserId},\n" +
            "STR_TO_DATE(#{param.createTime}, '%Y-%m-%d %H:%i:%s'),#{param.comment})")
    Integer insert(@Param("param") AuditUser auditUser);

    @Update("UPDATE T_BS_AUDIT_USER SET STATE=#{param.state},auditUserId=#{param.auditUserId},message=#{param.message},code=#{param.code} " +
            "WHERE auditId=#{param.auditId}")
     Integer audit(@Param("param") AuditUser auditUser);

    @Select("SELECT * FROM T_BS_AUDIT_USER WHERE AUDITID = #{auditId}")
    AuditUser getById(@Param("auditId") String auditId);

    @Select("SELECT * FROM T_BS_AUDIT_USER WHERE AUDITID = #{auditId} AND CODE= #{code} and code is not null")
    AuditUser validate(@Param("auditId") String auditId,@Param("code") String code);

    @Update("UPDATE T_BS_AUDIT_USER SET code=null WHERE auditId=#{auditId}")
    void active(@Param("auditId") String auditId);

    @Select("SELECT A.AUDITID,A.APPLYUSERID,A.CREATETIME,A.AUDITTIME,A.STATE,A.AUDITUSERID,A.COMMENT" +
            ",A.MESSAGE,B.USERNAME AS APPLYUSERNAME,B.EMAIL AS APPLYEMAIL,C.USERNAME AS AUDITUSERNAME\n" +
            " FROM T_BS_AUDIT_USER A JOIN t_bas_user B ON A.APPLYUSERID=B.USERID\n" +
            "LEFT JOIN t_bas_user C ON A.AUDITUSERID=C.USERID WHERE A.STATE=#{state}")
    List<AuditDetail> list(@Param("state") Integer state);

    @Select("SELECT userId,username FROM t_bas_user " +
            "WHERE upper(USERID) LIKE upper('%${keyword}%') " +
            "OR upper(USERNAME) LIKE upper('%${keyword}%')")
    List<User> listByKeyword(@Param("keyword") String keyword);
}
