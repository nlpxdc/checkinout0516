package io.cjf.checkinout0516.dao;

import io.cjf.checkinout0516.po.CheckInOut;

public interface CheckInOutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckInOut record);

    int insertSelective(CheckInOut record);

    CheckInOut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckInOut record);

    int updateByPrimaryKey(CheckInOut record);
}