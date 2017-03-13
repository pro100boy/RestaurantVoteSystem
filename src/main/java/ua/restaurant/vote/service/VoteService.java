package ua.restaurant.vote.service;

import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public interface VoteService {
    Vote save(VoteTo voteTo, int userId);

    void delete(int id, int userId) throws NotFoundException;

    Vote get(int id, int userId) throws NotFoundException;

    List<Vote> getAll(int userId);

    /**
     * get votes from specific user for all restaurants for period
     */
    List<Vote> getWithUserForPeriod(int userId, LocalDate startDate, LocalDate endDate);

    /**
     * get list of users who voted for the specified restaurant for period
     */
    List<Vote> getWithRestaurantForPeriod(int restaurantId, LocalDate startDate, LocalDate endDate);

    Vote update(VoteTo voteTo, int userId) throws NotFoundException;

    List<ResultTo> getResultSet(LocalDate date);

    void evictCache();
}
