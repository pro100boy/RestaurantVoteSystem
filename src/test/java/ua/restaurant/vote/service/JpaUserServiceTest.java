package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.VoteTestData;
import ua.restaurant.vote.model.Role;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.repository.JpaUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static ua.restaurant.vote.UserTestData.USER1_ID;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
public class JpaUserServiceTest extends AbstractUserServiceTest {
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
    }

    @Test
    public void testGetWithVotes() throws Exception {
        User user = service.getWithVotes(USER1_ID);
        //MATCHER.assertEquals(USER1, user);
        VoteTestData.MATCHER.assertCollectionEquals(VoteTestData.VOTES_USER, user.getVotes());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithVotesNotFound() throws Exception {
        service.getWithVotes(1);
    }

    @Test
    public void testGetWithVotesForPeriod() throws Exception {
        User user = service.getWithVotesForPeriod(USER1_ID, LocalDate.of(2016, Month.JANUARY, 30), LocalDate.of(2018, Month.JANUARY, 30));
        //MATCHER.assertEquals(USER1, user);
        VoteTestData.MATCHER.assertCollectionEquals(Arrays.asList(VoteTestData.VOTE6, VoteTestData.VOTE2), user.getVotes());
    }
}
