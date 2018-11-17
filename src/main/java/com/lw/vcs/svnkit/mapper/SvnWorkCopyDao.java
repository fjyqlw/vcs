package com.lw.vcs.svnkit.mapper;

import com.lw.vcs.svnkit.model.SvnWorkCopy;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 23:30
 * @Description：
 */
@Mapper
public interface SvnWorkCopyDao {
    @Select("SELECT * FROM t_bas_work_copy")
    List<SvnWorkCopy> list();

    @Insert("INSERT INTO t_bas_work_copy(svnId,workCopyName,svnUrl) " +
            "VALUES(#{param.svnId},#{param.workCopyName},#{param.svnUrl})")
    Integer insert(@Param("param") SvnWorkCopy svnWorkCopy);

    @Insert("UPDATE t_bas_work_copy SET workCopyName=#{workCopyName} WHERE svnId=#{svnId}")
    Integer update(@Param("svnId") String svnId, @Param("workCopyName")String workCopyName);

    @Delete("DELETE FROM t_bas_work_copy WHERE svnId=#{svnId}")
    Integer delete(@Param("svnId") String svnId);
}
