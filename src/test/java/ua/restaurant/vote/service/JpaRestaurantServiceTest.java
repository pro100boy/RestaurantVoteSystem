package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.RestaurantTestData;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.VoteTestData;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.repository.JpaUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import static ua.restaurant.vote.RestaurantTestData.MATCHER;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1_ID;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public class JpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
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

    @Test
    public void testGetWithVotes() throws Exception {
        Restaurant restaurant = service.getWithVotes(RESTAURANT1_ID);
        MATCHER.assertEquals(RESTAURANT1, restaurant);
        VoteTestData.MATCHER.assertCollectionEquals(VoteTestData.VOTES_REST, restaurant.getVotes());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithVotesNotFound() throws Exception {
        service.getWithVotes(1);
    }

    @Test
    public void testGetWithVotesForPeriod() throws Exception {
        Restaurant user = service.getWithVotesForPeriod(RESTAURANT1_ID, LocalDate.of(2016, Month.JANUARY, 30), LocalDate.of(2018, Month.JANUARY, 30));
        //MATCHER.assertEquals(USER1, user);
        System.out.println(user);
        //RestaurantTestData.MATCHER.assertCollectionEquals(Arrays.asList(RestaurantTestData.VOTE6, RestaurantTestData.VOTE2), user.getVotes());
    }
}
