package com.lw.vcs.svnkit.mapper;

import com.lw.vcs.svnkit.model.ApplyData;
import com.lw.vcs.svnkit.model.ServerFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author：lian.wei
 * @Date：2018/11/28 21:19
 * @Description：
 */
@Mapper
public interface SVNApplyDao {
    @Insert("INSERT INTO T_BS_FILE(FILEID,FILENAME,SERVERFILENAME,FILESIZE,UPLOADTIME)\n" +
            "VALUES(#{param.fileId},#{param.fileName},#{param.serverFileName},#{param.fileSize},NOW())")
    int insertFile(@Param("param") ServerFile serverFile);

    @Insert("<script>" +
            "INSERT INTO T_BS_SVN_APPLY(APPLYID,APPLYUSERID,TYPE,`NAME`,URL,`COMMENT`,FILEID,AUDITUSERID,APPLYTIME)\n" +
            "VALUES(#{param.applyId},#{param.applyUserId},#{param.type},#{param.name}" +
            ",#{param.url},#{param.comment},#{param.fileId},#{param.auditUserId},NOW())" +
            "</script>")
    void insertApply(@Param("param") ApplyData applyData);
}
