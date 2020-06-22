package com.dominikyang.library.dao;

import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogAdminDao {
    long countByExample(LogAdminExample example);

    int deleteByExample(LogAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogAdmin record);

    int insertSelective(LogAdmin record);

    List<LogAdmin> selectByExample(LogAdminExample example);

    LogAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogAdmin record, @Param("example") LogAdminExample example);

    int updateByExample(@Param("record") LogAdmin record, @Param("example") LogAdminExample example);

    int updateByPrimaryKeySelective(LogAdmin record);

    int updateByPrimaryKey(LogAdmin record);
}