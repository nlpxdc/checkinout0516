package io.cjf.checkinout0516.controller;

import com.alibaba.fastjson.JSONObject;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.cjf.checkinout0516.dao.CheckRecordMapper;
import io.cjf.checkinout0516.dao.UserMapper;
import io.cjf.checkinout0516.enumeration.CheckType;
import io.cjf.checkinout0516.enumeration.UserStatus;
import io.cjf.checkinout0516.exception.WebClientException;
import io.cjf.checkinout0516.po.CheckRecord;
import io.cjf.checkinout0516.po.User;
import io.cjf.checkinout0516.service.WechatMPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Value("${check.latitude}")
    private Double checkLatitude;

    @Value("${check.longitude}")
    private Double checkLongitude;

    @Value("${check.distance}")
    private Double checkDistance;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CheckRecordMapper checkRecordMapper;

    @Autowired
    private WechatMPService wechatMPService;

    @GetMapping("/canCheck")
    public void canCheck(@RequestParam Double latitude,
                         @RequestParam Double longitude) throws WebClientException {
        Coordinate lat = Coordinate.fromDegrees(checkLatitude);
        Coordinate lng = Coordinate.fromDegrees(checkLongitude);
        Point checkPosition = Point.at(lat, lng);

        lat = Coordinate.fromDegrees(latitude);
        lng = Coordinate.fromDegrees(longitude);
        Point userPosition = Point.at(lat, lng);

        double distance = EarthCalc.harvesineDistance(checkPosition, userPosition); //in meters
        if (distance > checkDistance) {
            throw new WebClientException("不在打卡范围");
        }
    }

    @GetMapping("/getCurrentStatus")
    public Byte getCurrentStatus(String openid){
        User user = userMapper.selectByPrimaryKey(openid);
        Byte status = user.getStatus();
        return status;
    }

    @PostMapping("/check")
    @Transactional
    public Integer check(@RequestParam String openid,
                      @RequestParam Byte type) throws WebClientException {

        User user = userMapper.selectByPrimaryKey(openid);
        Byte status = user.getStatus();
        if (type == CheckType.CheckIn.ordinal() && status == UserStatus.OnWorking.ordinal()){
            throw new WebClientException("已签到");
        }
        if (type == CheckType.CheckOut.ordinal() && status == UserStatus.OffWorking.ordinal()){
            throw new WebClientException("已签退");
        }

        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setOpenid(openid);
        checkRecord.setType(type);
        checkRecord.setTime(new Date());
        checkRecordMapper.insert(checkRecord);

        if (type == CheckType.CheckIn.ordinal()){
            user.setStatus((byte) UserStatus.OnWorking.ordinal());
        }
        if (type == CheckType.CheckOut.ordinal()){
            user.setStatus((byte) UserStatus.OffWorking.ordinal());
        }
        userMapper.updateByPrimaryKey(user);

        Integer id = checkRecord.getId();
        return id;
    }

    @GetMapping("/getToken")
    public JSONObject getToken(@RequestParam String code){
        JSONObject jsonObject = wechatMPService.getUserAccessToken(code);
        return jsonObject;
    }

}
