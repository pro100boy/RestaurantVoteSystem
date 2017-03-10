package ua.restaurant.vote.service;

import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    Restaurant getWithVotes(int id);

    Restaurant getWithVotesForPeriod(int id, LocalDate startDate, LocalDate endDate);

    List<Object[]> getWithMenu(LocalDate startDate, LocalDate endDate);
}
