package ua.restaurant.vote.service;

import ua.restaurant.vote.model.Menu;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 14.03.2017.
 */
public interface MenuService {
    Menu save(Menu menu, int restaurantId);
    Menu update(Menu menu, int restaurantId) throws NotFoundException;

    void delete(int id, int restaurantId) throws NotFoundException;;

    Menu get(int id, int restaurantId) throws NotFoundException;

    List<Menu> getAllWithRestaurant(int restaurantId);
    List<Menu> getAllWithRestaurant(int restaurantId, LocalDate startDate, LocalDate endDate);
}
