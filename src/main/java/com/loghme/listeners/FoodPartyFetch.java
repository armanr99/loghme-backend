package com.loghme.listeners;

import com.loghme.configs.FoodPartyConfigs;
import com.loghme.models.Restaurant.Restaurant;
import com.loghme.repositories.RestaurantRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class FoodPartyFetch implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final Runnable foodPartyRequester = () -> {
            try {
                RestaurantRepository.getInstance().clearPartyFoods();
                RestaurantRepository.getInstance().fetchFoodParties();
            } catch (Exception exception) {
                System.out.println("Error in fetching data: " + exception.toString());
            }
        };

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(foodPartyRequester, 0, FoodPartyConfigs.CHECK_TIME_MINUTE, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduler.shutdownNow();
    }
}
