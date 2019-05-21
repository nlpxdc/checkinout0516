package io.cjf.checkinout0516.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.cjf.checkinout0516.api.WechatMPApi;
import io.cjf.checkinout0516.component.UserPosition;
import io.cjf.checkinout0516.component.WechatMPVariable;
import io.cjf.checkinout0516.constant.ErrConstant;
import io.cjf.checkinout0516.constant.WechatConstant;
import io.cjf.checkinout0516.dao.CheckRecordMapper;
import io.cjf.checkinout0516.dao.UserDetailMapper;
import io.cjf.checkinout0516.dao.UserMapper;
import io.cjf.checkinout0516.enumeration.CheckType;
import io.cjf.checkinout0516.enumeration.UserStatus;
import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.po.CheckRecord;
import io.cjf.checkinout0516.po.User;
import io.cjf.checkinout0516.po.UserDetail;
import io.cjf.checkinout0516.service.UserService;
import io.cjf.checkinout0516.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPosition userPosition;

    @Autowired
    private WechatMPApi wechatMPApi;

    @Autowired
    private WechatMPVariable wechatMPVariable;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private CheckRecordMapper checkRecordMapper;

    @Value("${check.latitude}")
    private Double checkLatitude;

    @Value("${check.longitude}")
    private Double checkLongitude;

    @Value("${check.distance}")
    private Double checkDistance;

    @Override
    @Transactional
    public void create(User user, UserDetail userDetail) {
        userMapper.insert(user);
        userDetailMapper.insert(userDetail);
    }

    @Override
    @Transactional
    public void delete(String openid) {
        checkRecordMapper.deleteByOpenid(openid);
        userDetailMapper.deleteByPrimaryKey(openid);
        userMapper.deleteByPrimaryKey(openid);
    }

    @Override
    public void savePosition(String openId, Position position) {
        userPosition.put(openId, position);
    }

    @Override
    public Position loadPosition(String openId) {
        Position position = userPosition.get(openId);
        return position;
    }

    @Override
    @Transactional
    public void checkIn(String openid) throws ClientException {

        checkPosition(openid);

        //todo use redis? or mybatis cache second level
        User user = userMapper.selectByPrimaryKey(openid);
        if (user == null){
            throw new  ClientException(openid, ErrConstant.USER_NOT_EXIST, ErrConstant.USER_NOT_EXIST_TEXT);
        }
        Byte status = user.getStatus();
        if (status == UserStatus.OnWorking.ordinal()){
            throw new ClientException(openid, ErrConstant.ALREADY_CHECK_IN, ErrConstant.ALREADY_CHECK_IN_TEXT);
        }
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setOpenid(openid);
        checkRecord.setType((byte) CheckType.CheckIn.ordinal());
        checkRecord.setTime(new Date());
        checkRecordMapper.insert(checkRecord);

        user.setStatus((byte) UserStatus.OnWorking.ordinal());
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    @Transactional
    public void checkOut(String openid) throws ClientException {

        checkPosition(openid);

        User user = userMapper.selectByPrimaryKey(openid);
        if (user == null){
            throw new  ClientException(openid, ErrConstant.USER_NOT_EXIST, ErrConstant.USER_NOT_EXIST_TEXT);
        }
        Byte status = user.getStatus();

        if (status == UserStatus.OffWorking.ordinal()){
            throw new ClientException(openid, ErrConstant.ALREADY_CHECK_OUT, ErrConstant.ALREADY_CHECK_OUT_TEXT);
        }
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setOpenid(openid);
        checkRecord.setType((byte) CheckType.CheckOut.ordinal());
        checkRecord.setTime(new Date());
        checkRecordMapper.insert(checkRecord);

        user.setStatus((byte) UserStatus.OffWorking.ordinal());
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User getUserFromWechatMP(String openid) throws ClientException {
        JSONObject userInfo = wechatMPApi.getUserInfo(wechatMPVariable.getAccessToken(), openid, WechatConstant.ZH_CN_LANG);
        openid = userInfo.getString("openid");
        if (openid == null){
            throw new ClientException(openid, ErrConstant.CANNOT_GET_USER_FROM_WECHATMP, ErrConstant.CANNOT_GET_USER_FROM_WECHATMP_TEXT);
        }
        User user = new User();
        user.setOpenid(openid);
        user.setNickname(userInfo.getString("nickname"));
        user.setGender(userInfo.getByte("sex"));
        user.setAvatarUrl(userInfo.getString("headimgurl"));
        user.setStatus(((byte) UserStatus.OffWorking.ordinal()));
        return user;
    }

    private void checkPosition(String openid) throws ClientException {
        Position position = loadPosition(openid);

        if (position == null){
            throw new ClientException(openid, ErrConstant.CANNOT_GET_POSITION, ErrConstant.CANNOT_GET_POSITION_TEXT);
        }

        Coordinate lat = Coordinate.fromDegrees(checkLatitude);
        Coordinate lng = Coordinate.fromDegrees(checkLongitude);
        Point checkPosition = Point.at(lat, lng);

        lat = Coordinate.fromDegrees(position.getLatitude());
        lng = Coordinate.fromDegrees(position.getLongitude());
        Point userPosition = Point.at(lat, lng);

        double distance = EarthCalc.harvesineDistance(checkPosition, userPosition); //in meters
        if (distance > checkDistance) {
            throw new ClientException(openid, ErrConstant.EXCEED_DISTANCE, ErrConstant.EXCEED_DISTANCE_TEXT);
        }
    }
}
