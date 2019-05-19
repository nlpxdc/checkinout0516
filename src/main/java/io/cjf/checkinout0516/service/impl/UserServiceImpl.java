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
import io.cjf.checkinout0516.dao.UserDetailMapper;
import io.cjf.checkinout0516.dao.UserMapper;
import io.cjf.checkinout0516.enumeration.UserStatus;
import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.po.User;
import io.cjf.checkinout0516.po.UserDetail;
import io.cjf.checkinout0516.service.UserService;
import io.cjf.checkinout0516.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void savePosition(String openId, Position position) {
        userPosition.put(openId, position);
    }

    @Override
    public Position loadPosition(String openId) {
        Position position = userPosition.get(openId);
        return position;
    }

    @Override
    public void checkIn(String openId) throws ClientException {
        Position position = loadPosition(openId);

        if (position == null){
            throw new ClientException(ErrConstant.CANNOT_GET_POSITION, ErrConstant.CANNOT_GET_POSITION_TEXT);
        }

        Coordinate lat = Coordinate.fromDegrees(checkLatitude);
        Coordinate lng = Coordinate.fromDegrees(checkLongitude);
        Point checkPosition = Point.at(lat, lng);

        lat = Coordinate.fromDegrees(position.getLatitude());
        lng = Coordinate.fromDegrees(position.getLongitude());
        Point userPosition = Point.at(lat, lng);

        double distance = EarthCalc.harvesineDistance(checkPosition, userPosition); //in meters
        if (distance > checkDistance) {
            throw new ClientException(ErrConstant.EXCEED_DISTANCE, ErrConstant.EXCEED_DISTANCE_TEXT);
        }

    }

    @Override
    public User getUserFromWechatMP(String openid) throws ClientException {
        JSONObject userInfo = wechatMPApi.getUserInfo(wechatMPVariable.getAccessToken(), openid, WechatConstant.ZH_CN_LANG);
        openid = userInfo.getString("openid");
        if (openid == null){
            throw new ClientException(ErrConstant.CANNOT_GET_USER_FROM_WECHATMP, ErrConstant.CANNOT_GET_USER_FROM_WECHATMP_TEXT);
        }
        User user = new User();
        user.setOpenid(openid);
        user.setNickname(userInfo.getString("nickname"));
        user.setGender(userInfo.getByte("sex"));
        user.setAvatarUrl(userInfo.getString("headimgurl"));
        user.setStatus(((byte) UserStatus.OffWorking.ordinal()));
        return user;
    }
}
