package io.cjf.checkinout0516.dao;

import io.cjf.checkinout0516.po.CheckRecord;

public interface CheckRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckRecord record);

    int insertSelective(CheckRecord record);

    CheckRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckRecord record);

    int updateByPrimaryKey(CheckRecord record);
}