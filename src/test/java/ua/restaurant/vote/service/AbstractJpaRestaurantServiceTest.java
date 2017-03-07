package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.model.Role;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.repository.JpaUtil;

import javax.validation.ConstraintViolationException;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public class AbstractJpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
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
        validateRootCause(() -> service.save(new Restaurant(null, "  ", "Restaurant")), ConstraintViolationException.class);
        // empty description
        validateRootCause(() -> service.save(new Restaurant(null, "Name", "  ")), ConstraintViolationException.class);
        // description size < 5
        validateRootCause(() -> service.save(new Restaurant(null, "Name", "desc")), ConstraintViolationException.class);
        // empty name and description
        validateRootCause(() -> service.save(new Restaurant(null, "  ", " ")), ConstraintViolationException.class);
    }
}
