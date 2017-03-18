package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;

import static ua.restaurant.vote.RestaurantTestData.*;
import static ua.restaurant.vote.UserTestData.*;
import static ua.restaurant.vote.VoteTestData.MATCHER;
import static ua.restaurant.vote.VoteTestData.*;
import static ua.restaurant.vote.VoteTestData.getCreated;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractVoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService voteService;

    @Before
    public void setUp() throws Exception {
        voteService.evictCache();
    }

    @Test
    public void testSave() {
        voteService.save(USER1_ID, RESTAURANT2_ID);
        Vote created = getCreated();
        created.setId(100021);
        MATCHER.assertEquals(created, voteService.getVote(USER1_ID, LocalDate.now()));
    }

    @Test
    public void testDelete() throws Exception {
        voteService.delete(VOTE1_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(
                Arrays.asList(VOTE5),
                voteService.getWithUserForPeriod(ADMIN_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        voteService.delete(1, 1);
    }

    @Test
    public void testGet() throws Exception {
        Vote vote = voteService.get(VOTE1_ID, ADMIN_ID);
        MATCHER.assertEquals(VOTE1, vote);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        voteService.get(1, 1);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE5, VOTE1), voteService.getAll(ADMIN_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        voteService.update(USER2_ID, RESTAURANT1_ID);

        Vote expected = getCreated();
        expected.setId(100019);
        expected.setRestaurant(RESTAURANT1);

        Vote updated = voteService.getVote(USER2_ID, LocalDate.now());
        MATCHER.assertEquals(expected, updated);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateIllegal() throws Exception {
        voteService.update(ADMIN_ID, 0);
    }
}