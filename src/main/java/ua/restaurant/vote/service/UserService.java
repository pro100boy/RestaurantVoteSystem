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

    /* Голоса ЮЗЕРА по ВСЕМ РЕСТОРАНАМ за ПЕРИОД */
    /*
    SELECT u.ID, u.NAME, v.DATE_TIME, r.NAME FROM USERS u
    INNER JOIN VOTES v ON (u.ID = v.USER_ID) INNER JOIN RESTAURANTS r ON v.REST_ID = r.ID
    WHERE u.ID=100003
    AND v.DATE_TIME BETWEEN '2017-02-20 10:00:00' AND '2017-02-20 10:00:00'
    */
    //User getVotesForAllRestaurants(int id, LocalDate startDate, LocalDate endDate);
}
