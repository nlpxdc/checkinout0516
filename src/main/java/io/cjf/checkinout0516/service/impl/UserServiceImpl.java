package io.cjf.checkinout0516.service.impl;

import io.cjf.checkinout0516.component.UserPosition;
import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.service.UserService;
import io.cjf.checkinout0516.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPosition userPosition;

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
            throw new ClientException("3008", "cannot get user position");
        }

    }
}
