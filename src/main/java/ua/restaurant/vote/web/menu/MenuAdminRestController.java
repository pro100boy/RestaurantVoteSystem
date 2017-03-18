package ua.restaurant.vote.web.menu;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.restaurant.vote.model.Menu;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 14.03.2017.
 */
@RestController
@RequestMapping(value = MenuAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuAdminRestController extends AbstractMenuController {
    static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menus";

    @Override
    @GetMapping("/{id}")
    public Menu get(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        super.delete(id, restaurantId);
    }

    @Override
    @GetMapping
    public List<Menu> getAll(@PathVariable("restaurantId") int restaurantId) {
        return super.getAll(restaurantId);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Menu menu, @PathVariable("id") int id,
                       @PathVariable("restaurantId") int restaurantId) {
        super.update(menu, id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable("restaurantId") int restaurantId) {
        Menu created = super.create(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/between")
    public List<Menu> getBetween(@PathVariable("restaurantId") int restaurantId,
                                 @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                 @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getBetween(restaurantId, startDate, endDate);
    }
}
