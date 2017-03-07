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
}
