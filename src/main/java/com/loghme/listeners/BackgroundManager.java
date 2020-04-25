package com.loghme.listeners;

import com.loghme.configs.ServerConfigs;
import com.loghme.models.services.DeliveryService;
import com.loghme.models.services.RestaurantService;
import com.loghme.schedulers.FoodPartyScheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BackgroundManager implements ServletContextListener {

    @Override
    public final void contextInitialized(final ServletContextEvent sce) {
        fetchRestaurants();
        FoodPartyScheduler.getInstance().handleFoodParty();
    }

    @Override
    public final void contextDestroyed(final ServletContextEvent sce) {
        FoodPartyScheduler.getInstance().shutdown();
        DeliveryService.getInstance().shutdownDeliveries();
    }

    private void fetchRestaurants() {
        try {
            RestaurantService.getInstance().fetchRestaurants(ServerConfigs.DATA_URL);
        } catch (Exception ex) {
            System.out.println("Error in fetching restaurants data: " + ex.toString());
        }
    }
}
