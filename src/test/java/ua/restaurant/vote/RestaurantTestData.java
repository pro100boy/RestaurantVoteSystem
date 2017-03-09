package ua.restaurant.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.restaurant.vote.matcher.ModelMatcher;
import ua.restaurant.vote.model.Restaurant;

import java.util.*;

import static ua.restaurant.vote.model.BaseEntity.START_SEQ;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
public class RestaurantTestData {
    public static final ModelMatcher<Restaurant> MATCHER = ModelMatcher.of(Restaurant.class);

    public static final int RESTAURANT1_ID = START_SEQ + 4;
    public static final int RESTAURANT2_ID = START_SEQ + 5;
    public static final int RESTAURANT3_ID = START_SEQ + 6;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Restaurant 1","Description of restaurant 1");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "Restaurant 2","Description of restaurant 2");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID, "Restaurant 3","Description of restaurant 3");

    public static final List<Restaurant> RESTAURANTS = new LinkedList<>(Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3));

    public static Restaurant getCreated() {
        return new Restaurant(null, "Новый ресторан", "Новое описание");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Обновленный ресторан", "Обновленное описание");
    }
}
