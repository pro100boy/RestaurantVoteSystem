package ua.restaurant.vote.web.restaurants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.service.RestaurantService;

import java.util.List;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
public class AbstractRestaurantController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }
}
