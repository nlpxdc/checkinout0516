package io.cjf.checkinout0516.service;

import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.po.User;
import io.cjf.checkinout0516.po.UserDetail;
import io.cjf.checkinout0516.vo.Position;

public interface UserService {

    void create(User user, UserDetail userDetail);

    void savePosition(String openId, Position position);

    Position loadPosition(String openId);

    void checkIn(String openId) throws ClientException;

    User getUserFromWechatMP(String openId) throws ClientException;
}
