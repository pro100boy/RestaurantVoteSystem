package ua.restaurant.vote.service;

import ua.restaurant.vote.model.User;
import ua.restaurant.vote.to.UserTo;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 04.03.2017.
 */
public interface UserService {
    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(UserTo user);

    List<User> getAll();

    void update(User user);

    void evictCache();

    void enable(int id, boolean enable);

    User getWithVotes(int id);

    /* USER's votes for ALL RESTAURANTS for PERIOD */
    /*
    SELECT v.VOTE_DATE, r.NAME FROM RESTAURANTS r
    INNER JOIN VOTES v ON r.ID = v.REST_ID INNER JOIN USERS u ON v.USER_ID = u.ID
    WHERE u.ID=100003 AND v.VOTE_DATE BETWEEN '2016-02-20' AND '2018-02-20'
    */
    User getVotesForAllRestaurants(int id, LocalDate startDate, LocalDate endDate);
}
