package ua.restaurant.vote.service;

import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.to.RestaurantTo;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
public interface RestaurantService {
    Restaurant save(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    Restaurant getByName(String name) throws NotFoundException;

    List<Restaurant> getAll();

    void update(Restaurant restaurant);

    void evictCache();

    List<RestaurantTo> findAllForDate(LocalDate date);

    Restaurant getWithParamsForPeriod(int id, LocalDate startDate, LocalDate endDate);
}
