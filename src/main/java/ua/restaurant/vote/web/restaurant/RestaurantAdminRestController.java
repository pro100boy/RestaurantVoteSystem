package ua.restaurant.vote.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.to.RestaurantTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@RestController
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class RestaurantAdminRestController extends AbstractRestaurantController{
    static final String REST_URL = "/rest/admin/restaurants";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return super.getAll();
    }                                           //findAll

    @Override
    @GetMapping(value = "/polls", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> findAllForDate(@RequestParam(value = "date", required = false) LocalDate date) {
        return super.findAllForDate(date);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    //findOne
    public Restaurant get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam("name") String name) {
        return super.getByName(name);
    }       // findByName

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)  // save
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }                               // delete

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {             // save
        super.update(restaurant, id);
    }

    @GetMapping(value = "/{id}/between", produces = MediaType.APPLICATION_JSON_VALUE)               // getWithParamsForPeriod
    public Restaurant get(@PathVariable("id") int id,
                          @RequestParam(value = "startDate", required = false) LocalDate startDate,
                          @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getBetween(id, startDate, endDate);
    }
}
