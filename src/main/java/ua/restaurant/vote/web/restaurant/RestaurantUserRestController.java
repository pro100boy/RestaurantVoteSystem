package ua.restaurant.vote.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.restaurant.vote.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 10.03.2017.
 */
@RestController
@RequestMapping(RestaurantUserRestController.REST_URL)
public class RestaurantUserRestController  extends AbstractRestaurantController{
    static final String REST_URL = "/rest/restaurants";

    @Override
    @GetMapping(value = "/polls", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> findAllForDate(@RequestParam(value = "date", required = false) LocalDate date) {
        return super.findAllForDate(date);
    }
}
