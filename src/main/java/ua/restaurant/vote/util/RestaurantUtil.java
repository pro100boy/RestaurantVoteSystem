package ua.restaurant.vote.util;

import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.to.RestaurantTo;

/**
 * Created by Galushkin Pavel on 10.03.2017.
 */
public class RestaurantUtil {
    public static RestaurantTo asTo(Restaurant restaurant)
    {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMenus());
    }
}
