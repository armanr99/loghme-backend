package com.loghme.models.repositories;

import com.loghme.exceptions.DeliveryInfoNotFound;
import com.loghme.models.domain.DeliveryInfo.DeliveryInfo;
import com.loghme.models.mappers.DeliveryInfo.DeliveryInfoMapper;

import java.sql.SQLException;

public class DeliveryInfoRepository {
    private static DeliveryInfoRepository instance = null;

    public static DeliveryInfoRepository getInstance() {
        if (instance == null) {
            instance = new DeliveryInfoRepository();
        }
        return instance;
    }

    public DeliveryInfo getDeliveryInfo(int orderId) throws DeliveryInfoNotFound, SQLException {
        DeliveryInfo deliveryInfo = DeliveryInfoMapper.getInstance().find(orderId);

        if (deliveryInfo == null) {
            throw new DeliveryInfoNotFound(orderId);
        } else {
            return deliveryInfo;
        }
    }

    public void addDeliveryInfo(DeliveryInfo deliveryInfo) throws SQLException {
        DeliveryInfoMapper.getInstance().insert(deliveryInfo);
    }
}
