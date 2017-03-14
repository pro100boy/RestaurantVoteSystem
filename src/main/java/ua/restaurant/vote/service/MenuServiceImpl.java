package ua.restaurant.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.restaurant.vote.model.Menu;
import ua.restaurant.vote.repository.MenuRepository;
import ua.restaurant.vote.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ua.restaurant.vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Galushkin Pavel on 14.03.2017.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    RestaurantRepository restaurantRepository;
    
    @Override
    public Menu save(Menu menu, int restaurantId) {
        if (!menu.isNew() && get(menu.getId(), restaurantId) == null) {
            menu = null;
        }
        else {
            menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        }
        Assert.notNull(menu, "menu must not be null");
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Menu menu, int restaurantId) {
        return checkNotFoundWithId(save(menu, restaurantId), menu.getId());
    }

    @Override
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(menuRepository.delete(id, restaurantId) != 0, id);
    }

    @Override
    public Menu get(int id, int restaurantId) {
        Menu menu = menuRepository.findOne(id);
        return checkNotFoundWithId(menu != null && menu.getRestaurant().getId() == restaurantId ? menu : null, id);
    }

    @Override
    public List<Menu> getAllWithRestaurant(int restaurantId) {
        return menuRepository.getAllWithRestaurant(restaurantId);
    }

    @Override
    public List<Menu> getAllWithRestaurant(int restaurantId, LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        return menuRepository.getAllBetweenDates(restaurantId, startDate, endDate);
    }
}
