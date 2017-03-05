package ua.restaurant.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.restaurant.vote.matcher.ModelMatcher;
import ua.restaurant.vote.model.Role;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.util.PasswordUtil;

import java.util.Objects;

import static ua.restaurant.vote.model.BaseEntity.START_SEQ;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
public class UserTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final int ADMIN_ID = START_SEQ;
    public static final int USER_ID = START_SEQ + 1;

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (comparePassword(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            //&& Objects.equals(expected.getCaloriesPerDay(), actual.getCaloriesPerDay())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

    private static boolean comparePassword(String rawOrEncodedPassword, String password) {
        if (PasswordUtil.isEncoded(rawOrEncodedPassword)) {
            return rawOrEncodedPassword.equals(password);
        } else if (!PasswordUtil.isMatch(rawOrEncodedPassword, password)) {
            LOG.error("Password " + password + " doesn't match encoded " + password);
            return false;
        }
        return true;
    }

}