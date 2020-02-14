package com.loghme.Interface;

import com.loghme.Constants.Commands;
import com.loghme.Constants.GeneralConstants;
import com.loghme.Interface.Exceptions.UndefinedCommand;
import com.loghme.Loghme.Loghme;

import java.util.Scanner;

public class Interface {
    private Loghme loghme;

    public Interface() {
        loghme = new Loghme();
    }

    public void start() {
        Scanner input = new Scanner(System.in);

        while (input.hasNext()) {
            String inputLine = input.nextLine();
            processLine(inputLine);
        }
    }

    private void processLine(String line) {
        String[] lineParts = splitBySpace(line);
        String command = lineParts[0];

        try {
            switch (command) {
                case Commands.ADD_RESTAURANT:
                    loghme.addRestaurant(lineParts[1]);
                    break;
                case Commands.ADD_FOOD:
                    loghme.addFood(lineParts[1]);
                    break;
                case Commands.GET_RESTAURANTS:
                    System.out.println(loghme.getRestaurants());
                    break;
                case Commands.GET_RESTAURANT:
                    System.out.println(loghme.getRestaurant(lineParts[1]));
                    break;
                case Commands.GET_FOOD:
                    System.out.println(loghme.getFood(lineParts[1]));
                    break;
                case Commands.ADD_TO_CART:
                    loghme.addToCart(lineParts[1]);
                    break;
                case Commands.GET_CART:
                    System.out.println(loghme.getCart());
                    break;
                case Commands.FINALIZE_ORDER:
                    String result = loghme.finalizeOrder();
                    System.out.println(result);
                    System.out.println("Order was successfully submitted");
                    break;
                case Commands.GET_RECOMMENDED_RESTAURANTS:
                    System.out.println(loghme.getRecommendedRestaurants());
                    break;
                default:
                    throw new UndefinedCommand(command);
            }
        } catch(Exception exception) {
            System.out.println("Error: " + exception.toString());
        }
    }

    private String[] splitBySpace(String string) {
        return string.split(GeneralConstants.SPACE, 2);
    }
}
