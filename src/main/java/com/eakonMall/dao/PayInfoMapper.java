package com.eakonMall.dao;

import com.eakonMall.pojo.PayInfo;
import org.springframework.stereotype.Repository;

@Repository(value = "payInfoMapper")
public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}