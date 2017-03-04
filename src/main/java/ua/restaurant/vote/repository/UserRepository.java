package ua.restaurant.vote.repository;

import ua.restaurant.vote.model.User;

import java.util.List;

/**
 * Created by Galushkin Pavel on 04.03.2017.
 */
public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

}
