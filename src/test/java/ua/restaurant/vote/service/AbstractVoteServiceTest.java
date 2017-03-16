package ua.restaurant.vote.service;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.repository.VoteRepository;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;

import static ua.restaurant.vote.RestaurantTestData.*;
import static ua.restaurant.vote.UserTestData.ADMIN_ID;
import static ua.restaurant.vote.UserTestData.USER1_ID;
import static ua.restaurant.vote.VoteTestData.MATCHER;
import static ua.restaurant.vote.VoteTestData.*;
import static ua.restaurant.vote.VoteTestData.getCreated;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public /*abstract*/ class AbstractVoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService service;
    @Autowired
    VoteRepository voteRepository;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @After
    public void after()
    {
        TestUtil.prntCollect(voteRepository.getAll());
    }

    @Test
    public void testSave() {
        Vote created = getCreated();
        created.setId(100021);
        MATCHER.assertEquals(created, service.save(USER1_ID, RESTAURANT2_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(VOTE1_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(
                Arrays.asList(VOTE5),
                service.getWithUserForPeriod(ADMIN_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, 1);
    }

    @Test
    public void testGet() throws Exception {
        Vote vote = service.get(VOTE1_ID, ADMIN_ID);
        MATCHER.assertEquals(VOTE1, vote);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, 1);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE5, VOTE1), service.getAll(ADMIN_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        service.save(ADMIN_ID, RESTAURANT1_ID);
        service.update(ADMIN_ID, RESTAURANT2_ID);

        Vote expected = getCreated();
        expected.setId(100021);
        expected.setRestaurant(RESTAURANT2);

        MATCHER.assertEquals(expected, service.getVote(ADMIN_ID, LocalDate.now()));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateIllegal() throws Exception {
        service.update(ADMIN_ID, 0);
    }
}