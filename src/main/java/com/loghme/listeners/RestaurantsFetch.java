package com.loghme.listeners;

import com.loghme.configs.ServerConfigs;
import com.loghme.models.Restaurant.Exceptions.RestaurantAlreadyExists;
import com.loghme.repositories.RestaurantRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RestaurantsFetch implements ServletContextListener {

    @Override
    public final void contextInitialized(final ServletContextEvent sce) {
        try {
            RestaurantRepository.getInstance().fetchData(ServerConfigs.DATA_URL);
        } catch (RestaurantAlreadyExists restaurantAlreadyExists) {
            System.out.println("Error in fetching data: " + restaurantAlreadyExists.toString());
        }
    }

    @Override
    public final void contextDestroyed(final ServletContextEvent sce) {}
}
