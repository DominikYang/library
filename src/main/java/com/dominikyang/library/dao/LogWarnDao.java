package com.dominikyang.library.dao;

import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.entity.LogWarnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogWarnDao {
    long countByExample(LogWarnExample example);

    int deleteByExample(LogWarnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogWarn record);

    int insertSelective(LogWarn record);

    List<LogWarn> selectByExample(LogWarnExample example);

    LogWarn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogWarn record, @Param("example") LogWarnExample example);

    int updateByExample(@Param("record") LogWarn record, @Param("example") LogWarnExample example);

    int updateByPrimaryKeySelective(LogWarn record);

    int updateByPrimaryKey(LogWarn record);
}