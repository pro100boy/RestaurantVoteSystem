package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.RestaurantTestData;
import ua.restaurant.vote.ResultTestData;
import ua.restaurant.vote.UserTestData;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.repository.JpaUtil;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1_ID;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT2;
import static ua.restaurant.vote.UserTestData.USER1_ID;
import static ua.restaurant.vote.VoteTestData.*;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
public class JpaVoteServiceTest extends AbstractVoteServiceTest {
    @Autowired
    private JpaUtil jpaUtil;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testGetWithUserForPeriod() throws Exception {
        List<Vote> votes = service.getWithUserForPeriod(USER1_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE);

        MATCHER.assertCollectionEquals(Arrays.asList(VOTE6, VOTE2), service.getWithUserForPeriod(USER1_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
        RestaurantTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(RestaurantTestData.RESTAURANT1, RESTAURANT2),
                votes.stream().map(u -> u.getRestaurant()).collect(Collectors.toList()));
    }

    @Test
    public void testGetWithUserForPeriodNotFound() throws Exception {
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getWithUserForPeriod(1, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test
    public void testGetWithRestaurantForPeriod() throws Exception {
        List<Vote> votes = service.getWithRestaurantForPeriod(RESTAURANT1_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE);

        MATCHER.assertCollectionEquals(Arrays.asList(VOTE8, VOTE6, VOTE5, VOTE1), votes);
        UserTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(UserTestData.USER3, UserTestData.USER1, UserTestData.ADMIN, UserTestData.ADMIN),
                votes.stream().map(v -> v.getUser()).collect(Collectors.toList()));
    }

    @Test
    public void testGetWithRestaurantForPeriodNotFound() throws Exception {
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getWithRestaurantForPeriod(1, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test
    public void testGetVoteResult() throws Exception {
        List<ResultTo> resultSet = service.getResultSet(LocalDate.of(2017, Month.JANUARY, 30));
        ResultTestData.MATCHER.assertCollectionEquals(ResultTestData.RESULT_TO_LIST, resultSet);
    }
}
