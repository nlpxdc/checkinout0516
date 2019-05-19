package io.cjf.checkinout0516.service;

import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.vo.Position;

public interface UserService {
    void savePosition(String openId, Position position);

    Position loadPosition(String openId);

    void checkIn(String openId) throws ClientException;
}
