package ua.restaurant.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Menu;
import ua.restaurant.vote.service.MenuService;
import ua.restaurant.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ua.restaurant.vote.util.ValidationUtil.checkIdConsistent;
import static ua.restaurant.vote.util.ValidationUtil.checkNew;

/**
 * Created by Galushkin Pavel on 09.03.2017.
 */
public abstract class AbstractMenuController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService service;

    public Menu create(Menu menu, int restaurantId) {
        checkNew(menu);
        log.info("create {} for Restaurant {}", menu, restaurantId);
        return service.save(menu, restaurantId);
    }

    public void update(Menu menu, int id, int restaurantId) {
        checkIdConsistent(menu, id);
        log.info("update {} for Restaurant {}", menu, restaurantId);
        service.update(menu, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        log.info("delete menu {} for Restaurant {}", id, restaurantId);
        service.delete(id, restaurantId);
    }

    public Menu get(int id, int restaurantId) {
        log.info("get menu {} for Restaurant {}", id, restaurantId);
        return service.get(id, restaurantId);
    }

    public List<Menu> getAll(int restaurantId) {
        log.info("getAll for Restaurant {}", restaurantId);
        return service.getAllWithRestaurant(restaurantId);
    }

    public List<Menu> getBetween(int restaurantId, LocalDate startDate, LocalDate endDate) {
        log.info("getBetween dates {} - {} for Restaurant {}", startDate, endDate, restaurantId);

        return service.getAllWithRestaurant(
                restaurantId,
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE
        );
    }
}
