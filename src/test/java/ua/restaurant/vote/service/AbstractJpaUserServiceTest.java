package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Role;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.repository.JpaUtil;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
abstract public class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JpaUtil jpaUtil;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testValidation() throws Exception {
        // empty name
        validateRootCause(() -> service.save(new User(null, "  ", "invalid@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        // empty email
        validateRootCause(() -> service.save(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        // empty password
        validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        //validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", "password", true, Collections.emptySet())), ConstraintViolationException.class);
        //validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", "password", true, Collections.emptySet())), ConstraintViolationException.class);
    }
}
