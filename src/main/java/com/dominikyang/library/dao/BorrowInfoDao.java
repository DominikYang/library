package com.dominikyang.library.dao;

import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.entity.BorrowInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowInfoDao {
    long countByExample(BorrowInfoExample example);

    int deleteByExample(BorrowInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowInfo record);

    int insertSelective(BorrowInfo record);

    List<BorrowInfo> selectByExample(BorrowInfoExample example);

    BorrowInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowInfo record, @Param("example") BorrowInfoExample example);

    int updateByExample(@Param("record") BorrowInfo record, @Param("example") BorrowInfoExample example);

    int updateByPrimaryKeySelective(BorrowInfo record);

    int updateByPrimaryKey(BorrowInfo record);
}