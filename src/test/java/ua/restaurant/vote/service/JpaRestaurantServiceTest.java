package ua.restaurant.vote.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.MenuTestData;
import ua.restaurant.vote.VoteTestData;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.to.RestaurantTo;
import ua.restaurant.vote.repository.JpaUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static ua.restaurant.vote.RestaurantTestData.*;

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
    public void testGetWithParamsForPeriod() throws Exception {
        Restaurant restaurant = service.getWithParamsForPeriod(RESTAURANT1_ID, LocalDate.of(2016, Month.JANUARY, 30), LocalDate.of(2018, Month.JANUARY, 30));
        MATCHER.assertEquals(RESTAURANT1, restaurant);
        VoteTestData.MATCHER.assertCollectionEquals(VoteTestData.VOTES_REST, restaurant.getVotes());
    }

    @Test
    public void testFindAllForDate() throws Exception {
        List<RestaurantTo> restaurantsTo = service.findAllForDate(LocalDate.of(2017, Month.JANUARY, 30));
        Assert.assertEquals(restaurantsTo.size(), 3);
        MenuTestData.MATCHER.assertCollectionEquals(restaurantsTo.stream().flatMap(m->m.getMenus().stream()).collect(Collectors.toList()), MenuTestData.MENUS);
    }
}
