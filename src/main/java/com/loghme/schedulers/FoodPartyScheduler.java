package com.loghme.schedulers;

import com.loghme.configs.FoodPartyConfigs;
import com.loghme.models.services.RestaurantService;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FoodPartyScheduler {
    private Date restartDate;
    private ScheduledExecutorService scheduler;
    private static FoodPartyScheduler instance = null;

    public static FoodPartyScheduler getInstance() {
        if(instance == null)
            instance = new FoodPartyScheduler();
        return instance;
    }

    private FoodPartyScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void handleFoodParty() {
        final Runnable foodPartyRequester = () -> {
            try {
                restartDate = new Date();
                RestaurantService.getInstance().clearPartyFoods();
                RestaurantService.getInstance().fetchFoodParties();
            } catch (Exception exception) {
                System.out.println("Error in fetching data: " + exception.toString());
            }
        };

        scheduler.scheduleAtFixedRate(foodPartyRequester, 0, FoodPartyConfigs.CHECK_TIME_MINUTE, TimeUnit.MINUTES);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public long getRemainingSeconds() {
        long timeDiff = new Date().getTime() - restartDate.getTime();
        long timeDiffSeconds = timeDiff / 1000;
        long checkTime = FoodPartyConfigs.CHECK_TIME_MINUTE * 60;

        return (checkTime  - timeDiffSeconds < 0 ? 0 : checkTime - timeDiffSeconds);
    }
}
