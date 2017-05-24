package com.eakonMall.dao;

import com.eakonMall.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository(value = "categoryMapper")
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectChildrenByParentId(Integer parentId);
}