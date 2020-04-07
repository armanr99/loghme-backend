package com.loghme.controllers.wrappers.Order;

import com.loghme.configs.DeliveryConfigs;
import com.loghme.models.Order.DeliveryInfo;

import java.util.Date;

public class DeliveryInfoWrapper {
    private long totalTime;
    private Date startDate;
    private String state;

    //TODO: improve this part
    DeliveryInfoWrapper(DeliveryInfo deliveryInfo) {
        this.totalTime = deliveryInfo == null ? 0 : deliveryInfo.getTotalTime();
        this.startDate = deliveryInfo == null ? new Date() : deliveryInfo.getStartDate();
        this.state = deliveryInfo == null ? DeliveryConfigs.State.SEARCHING : deliveryInfo.getState();
    }
}
