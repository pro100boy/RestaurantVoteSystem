package ua.restaurant.vote.service;

import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.util.Collection;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public interface VoteService {
    Vote get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Collection<Vote> getAll(int userId);

    //Collection<Vote> getBetweenDatesWithRestaurant(int userId, LocalDate startDate, LocalDate endDate);

    Vote update(Vote vote, int userId, int restaurantId) throws NotFoundException;

    Vote save(Vote vote, int userId, int restaurantId);

    Vote getWithUser(int id, int userId);
}
