package io.cjf.checkinout0516.dao;

import io.cjf.checkinout0516.po.CheckInOut;
import org.apache.ibatis.annotations.Param;

public interface CheckInOutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckInOut record);

    int insertSelective(CheckInOut record);

    CheckInOut selectByPrimaryKey(Integer id);

    //todo select by openid and type
    CheckInOut selectRecentByOpenidType(@Param("openid") String openid, @Param("type") Integer type);

    int updateByPrimaryKeySelective(CheckInOut record);

    int updateByPrimaryKey(CheckInOut record);
}