package ua.restaurant.vote.service;

import ua.restaurant.vote.model.User;
import ua.restaurant.vote.to.UserTo;
import ua.restaurant.vote.util.exception.NotFoundException;

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
}
