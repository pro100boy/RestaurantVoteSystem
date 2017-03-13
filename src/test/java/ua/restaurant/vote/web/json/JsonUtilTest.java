package ua.restaurant.vote.web.json;

import org.junit.Test;
import ua.restaurant.vote.RestaurantTestData;
import ua.restaurant.vote.model.Restaurant;

import java.util.List;

/**
 * Created by Galushkin Pavel on 04.03.2017.
 */
public class JsonUtilTest {
    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(RestaurantTestData.RESTAURANT1);
        System.out.println(json);
        Restaurant restaurant = JsonUtil.readValue(json, Restaurant.class);
        RestaurantTestData.MATCHER.assertEquals(RestaurantTestData.RESTAURANT1, restaurant);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(RestaurantTestData.RESTAURANTS);
        System.out.println(json);
        List<Restaurant> restaurants = JsonUtil.readValues(json, Restaurant.class);
        RestaurantTestData.MATCHER.assertCollectionEquals(RestaurantTestData.RESTAURANTS, restaurants);
    }
}