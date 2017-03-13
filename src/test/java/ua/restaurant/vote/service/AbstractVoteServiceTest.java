package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.util.Arrays;

import static ua.restaurant.vote.UserTestData.ADMIN_ID;
import static ua.restaurant.vote.UserTestData.USER1_ID;
import static ua.restaurant.vote.VoteTestData.*;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractVoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testSave() throws Exception {
        VoteTo newVoteTo = getCreatedTo();
        Vote created = service.save(newVoteTo, ADMIN_ID);
        MATCHER.assertCollectionEquals(
                Arrays.asList(created, VOTE5, VOTE1),
                service.getWithUserForPeriod(ADMIN_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
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
        VoteTo updatedTo = getUpdatedTo();
        Vote updated = service.update(updatedTo, ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(VOTE1_ID, ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        VoteTo updatedTo = getUpdatedTo();
        Vote updated = service.update(updatedTo, ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(VOTE1_ID, USER1_ID));
    }
}
