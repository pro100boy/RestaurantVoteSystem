package ua.restaurant.vote.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.restaurant.vote.model.Restaurant;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminRestController extends AbstractRestaurantController{
    static final String REST_URL = "/rest/admin/restaurants";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @GetMapping(value = "/{id}")
    public Restaurant get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        super.update(restaurant, id);
    }

    @GetMapping(value = "/{id}/between")
    public Restaurant get(@PathVariable("id") int id,
                          @RequestParam(value = "startDate", required = false) LocalDate startDate,
                          @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getBetween(id, startDate, endDate);
    }
}
