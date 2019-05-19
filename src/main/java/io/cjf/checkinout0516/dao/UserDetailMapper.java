package io.cjf.checkinout0516.dao;

import io.cjf.checkinout0516.po.UserDetail;

public interface UserDetailMapper {
    int deleteByPrimaryKey(String openid);

    int insert(UserDetail record);

    int insertSelective(UserDetail record);

    UserDetail selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(UserDetail record);

    int updateByPrimaryKey(UserDetail record);
}