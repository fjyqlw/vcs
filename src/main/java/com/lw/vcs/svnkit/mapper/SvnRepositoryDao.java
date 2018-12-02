package com.lw.vcs.svnkit.mapper;

import com.lw.vcs.svnkit.model.SvnRepository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/26 19:38
 * @Description：
 */
@Mapper
public interface SvnRepositoryDao {
    @Select("SELECT * FROM T_BAS_SVN_REPO WHERE SVNID=#{svnId} and STATE=1")
    public SvnRepository getAvailableById(@Param("svnId") String svnId);

    @Select("SELECT * FROM T_BAS_SVN_REPO WHERE SVNURL=#{svnUrl}")
    public SvnRepository getByUrl(@Param("svnUrl") String svnUrl);

    @Insert("INSERT INTO T_BAS_SVN_REPO(SVNID,SVNNAME,SVNURL) " +
            "VALUES(#{param.svnId},#{param.svnName},#{param.svnUrl})")
    Integer addRepository(@Param("param") SvnRepository svnRepository);

    @Select("SELECT * FROM T_BAS_SVN_REPO")
    List<SvnRepository> list();

    @Select("SELECT * FROM T_BAS_SVN_REPO WHERE SVNID=#{svnId}")
    SvnRepository getById(@Param("svnId") String svnId);
}
