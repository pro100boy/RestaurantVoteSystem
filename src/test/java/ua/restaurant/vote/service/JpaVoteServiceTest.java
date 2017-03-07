package ua.restaurant.vote.service;

import org.junit.Test;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import static ua.restaurant.vote.UserTestData.ADMIN_ID;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1_ID;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public class JpaVoteServiceTest extends AbstractVoteServiceTest {
    @Test
    public void testValidation() throws Exception {
        // empty datetime
        validateRootCause(() -> service.save(new Vote(null, null), ADMIN_ID, RESTAURANT1_ID), ConstraintViolationException.class);
    }

/*    @Test
    public void testGetWithUser() throws Exception {
        Vote adminVote = service.getWithUser(ADMIN_VOTE_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, adminMeal);
        UserTestData.MATCHER.assertEquals(UserTestData.ADMIN, adminMeal.getUser());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithUserNotFound() throws Exception {
        service.getWithUser(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void testGetWithRestaurant() throws Exception {
        Meal adminMeal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, adminMeal);
        UserTestData.MATCHER.assertEquals(UserTestData.ADMIN, adminMeal.getUser());
    }

    @Test(expected = NotFoundException.class)
    public void ttestGetWithRestaurantNotFound() throws Exception {
        service.getWithUser(MEAL1_ID, ADMIN_ID);
    }*/
}
