package com.lw.vcs.auth.mapper;

import com.lw.vcs.auth.model.RegisterVO;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.auth.model.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/8/7 22:09
 * @Description：
 */
@Mapper
public interface UserDao {

    @Select("select * from T_BAS_USER where userId = #{userId}")
    public User getById(@Param("userId") String userId);

    @Select("select * from T_BAS_USER")
    public List<User> list();

    @Select("select * from T_BAS_USER WHERE STATE=#{state}")
    public List<User> listByState(@Param("state")Integer state);

    @Insert("INSERT INTO T_BAS_USER(USERID,USERNAME,EMAIL) " +
            "VALUES(#{param.userId},#{param.userName},#{param.email})")
    public Integer insert(@Param("param") UserVO userVO);

    @Insert("INSERT INTO T_BAS_USER(USERID,USERNAME,EMAIL) " +
            "VALUES(#{param.userId},#{param.userName},#{param.email})")
    public Integer register(@Param("param") RegisterVO registerVO);

    @Update("UPDATE T_BAS_USER SET STATE=#{state} WHERE USERID=#{userId}")
    public Integer audit(@Param("userId") String userId,@Param("state") Integer state);

    @Update("UPDATE T_BAS_USER SET password=#{password},salt=#{salt},STATE=1 WHERE USERID=#{userId}")
    void password(@Param("userId") String userId,@Param("password") String password,@Param("salt") String salt);
}
