package com.lw.vcs.svnkit.mapper;

import com.lw.vcs.audit.model.AuditFile;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 17:08
 * @Description：
 */
@Mapper
public interface SvnDao {
    @Select("SELECT A.id as userId,B.svnUserId,C.`password` ,D.svnId FROM t_bas_user A\n" +
            "JOIN t_cfg_user_svnuser_rel B\n" +
            "ON A.id=B.userId\n" +
            "JOIN t_bas_svnuser C\n" +
            "ON B.svnUserId=C.svnUserId\n" +
            "JOIN t_cfg_user_svnwc_rel D\n" +
            "ON C.svnUserId=D.svnUserId\n" +
            "WHERE A.id=#{id} and D.svnId=#{svnId}")
    SvnUserCfg getSVNUserCfg(@Param("id") Long id, @Param("id") String svnId);

    @Select("SELECT A.id as userId,B.svnUserId,C.`password` as svnPassword FROM t_bas_user A\n" +
            "JOIN t_cfg_user_svnuser_rel B\n" +
            "ON A.id=B.userId\n" +
            "JOIN t_bas_svnuser C\n" +
            "ON B.svnUserId=C.svnUserId\n" +
            "WHERE A.id=#{id} ")
    SvnUserCfg getSVNUserByUserId(@Param("id") String id);

    @Insert("INSERT INTO t_bs_audit_file(FILEID,FILENAME,FILESVNURL,SVNUSERID,COMMENT,AUDITUSERID)\n" +
            "VALUES(#{param.fileId},#{param.fileName},#{param.svnfileSvnUrl},#{param.svnUserId},#{param.comment},#{param.auditUserId})")
    Integer insertAuditFile(@Param("param") AuditFile auditFile);

    @Select("SELECT * FROM t_bs_audit_file WHERE FILEID = #{fileId}")
    AuditFile queryAuditFile(@Param("fileId") String fileId);
}
