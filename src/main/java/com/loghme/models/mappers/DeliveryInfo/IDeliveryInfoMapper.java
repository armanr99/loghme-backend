package com.loghme.models.mappers.DeliveryInfo;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.DeliveryInfo.DeliveryInfo;

import java.sql.SQLException;

public interface IDeliveryInfoMapper extends IMapper<DeliveryInfo> {
    DeliveryInfo find(int orderId) throws SQLException;
    void insert(DeliveryInfo deliveryInfo) throws SQLException;
}
