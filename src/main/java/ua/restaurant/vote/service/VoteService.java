package ua.restaurant.vote.service;

import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public interface VoteService {
    Vote save(Vote vote, int userId, int restaurantId);

    void delete(int id, int userId) throws NotFoundException;

    Vote getWithUser(int id, int userId) throws NotFoundException;

    List<Vote> getWithUserForPeriod(int userId, LocalDate startDate, LocalDate endDate);

    List<Vote> getWithRestaurantForPeriod(int restaurantId, LocalDate startDate, LocalDate endDate);

    Vote update(Vote vote, int userId, int restaurantId) throws NotFoundException;

    List<VoteTo> getResultSet(LocalDate date);
}
