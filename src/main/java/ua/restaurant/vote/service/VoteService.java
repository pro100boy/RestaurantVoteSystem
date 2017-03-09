package ua.restaurant.vote.service;

import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public interface VoteService {
    Vote save(Vote vote, int userId, int restaurantId);

    void delete(int id, int userId) throws NotFoundException;

    Vote get(int id, int userId) throws NotFoundException;

    List<Vote> getAllWithUser(int userId);

    //Collection<Vote> getBetweenDatesWithRestaurant(int userId, LocalDate startDate, LocalDate endDate);

    Vote update(Vote vote, int userId, int restaurantId) throws NotFoundException;
}
