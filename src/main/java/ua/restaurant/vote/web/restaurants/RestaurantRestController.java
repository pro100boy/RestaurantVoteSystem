package ua.restaurant.vote.web.restaurants;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.restaurant.vote.model.Restaurant;

import java.util.List;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController extends AbstractRestaurantController{
    static final String REST_URL = "/rest/admin/restaurants";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return super.getAll();
    }
}
