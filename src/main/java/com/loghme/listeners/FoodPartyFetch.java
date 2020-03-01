package com.loghme.listeners;

import com.loghme.configs.FoodPartyConfigs;
import com.loghme.configs.ServerConfigs;
import com.loghme.utils.HTTPRequester;

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
            String foodPartyJson = HTTPRequester.get(ServerConfigs.FOOD_PARTY_URL);
        };

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(foodPartyRequester, 0, FoodPartyConfigs.CHECK_TIME_MINUTE, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduler.shutdownNow();
    }
}
