package ua.restaurant.vote.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.util.exception.NotFoundException;
import ua.restaurant.vote.util.exception.VoteException;

import javax.validation.ConstraintViolationException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1_ID;
import static ua.restaurant.vote.UserTestData.ADMIN_ID;
import static ua.restaurant.vote.UserTestData.USER1_ID;
import static ua.restaurant.vote.VoteTestData.*;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService service;

    @Test
    public void testValidation() throws Exception {
        // empty datetime
        validateRootCause(() -> service.save(new Vote(null, null), ADMIN_ID, RESTAURANT1_ID), ConstraintViolationException.class);
    }

    @Test
    public void testSave() throws Exception {
        Vote newVote = getCreated();
        Vote created = service.save(newVote, ADMIN_ID, RESTAURANT1_ID);
        newVote.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE5, newVote, VOTE1), service.getWithUserForPeriod(ADMIN_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test
    public void testGetWithUserForPeriod() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE6, VOTE2), service.getWithUserForPeriod(USER1_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test
    public void testGetWithUserForPeriodNotFound() throws Exception {
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getWithUserForPeriod(1, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test
    public void testGetWithRestaurantForPeriod() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE8, VOTE6, VOTE5, VOTE1), service.getWithRestaurantForPeriod(RESTAURANT1_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test
    public void testGetWithRestaurantForPeriodNotFound() throws Exception {
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getWithRestaurantForPeriod(1, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(VOTE1_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE5), service.getWithUserForPeriod(ADMIN_ID, DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, 1);
    }

    @Test
    public void testGet() throws Exception {
        Vote vote = service.getWithUser(VOTE1_ID, ADMIN_ID);
        MATCHER.assertEquals(VOTE1, vote);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.getWithUser(1, 1);
    }

    @Test
    public void testUpdate() throws Exception {
        Vote updated = getUpdatedVote();
        MATCHER.assertEquals(updated, service.getWithUser(VOTE1_ID, ADMIN_ID));
    }

    private Vote getUpdatedVote() {
        Vote updated = getUpdated(); //new Vote(VOTE1_ID, of(2017, Month.JANUARY, 8));
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().plusMinutes(1));
        service.update(updated, ADMIN_ID, RESTAURANT1_ID);
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
        return updated;
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Vote updated = getUpdatedVote();
        MATCHER.assertEquals(updated, service.getWithUser(VOTE1_ID, USER1_ID));
    }

    @Test(expected = VoteException.class)
    public void testUpdateDeny() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().minusMinutes(1));
        service.update(VOTE1, ADMIN_ID, RESTAURANT1_ID);
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
    }
}
