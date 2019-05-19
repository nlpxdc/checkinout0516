package io.cjf.checkinout0516.service.impl;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.cjf.checkinout0516.component.UserPosition;
import io.cjf.checkinout0516.constant.ErrConstant;
import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.service.UserService;
import io.cjf.checkinout0516.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPosition userPosition;

    @Value("${check.latitude}")
    private Double checkLatitude;

    @Value("${check.longitude}")
    private Double checkLongitude;

    @Value("${check.distance}")
    private Double checkDistance;

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
}
