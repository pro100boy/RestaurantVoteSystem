package ua.restaurant.vote.util;

import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 10.03.2017.
 */
public class RestaurantUtil {
    private RestaurantUtil(){}

    public static RestaurantTo asTo(Restaurant restaurant)
    {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMenus());
    }

    public static List<RestaurantTo> asToList(List<Restaurant> restaurantList)
    {
        return restaurantList.stream().map(r->RestaurantUtil.asTo(r)).collect(Collectors.toList());
    }
}
