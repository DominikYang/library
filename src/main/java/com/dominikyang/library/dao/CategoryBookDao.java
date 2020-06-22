package com.dominikyang.library.dao;

import com.dominikyang.library.entity.CategoryBook;
import com.dominikyang.library.entity.CategoryBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryBookDao {
    long countByExample(CategoryBookExample example);

    int deleteByExample(CategoryBookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CategoryBook record);

    int insertSelective(CategoryBook record);

    List<CategoryBook> selectByExample(CategoryBookExample example);

    CategoryBook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CategoryBook record, @Param("example") CategoryBookExample example);

    int updateByExample(@Param("record") CategoryBook record, @Param("example") CategoryBookExample example);

    int updateByPrimaryKeySelective(CategoryBook record);

    int updateByPrimaryKey(CategoryBook record);
}