package ua.restaurant.vote.web.vote;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.RestaurantTestData;
import ua.restaurant.vote.ResultTestData;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.VoteTestData;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.web.AbstractControllerTest;
import ua.restaurant.vote.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.restaurant.vote.RestaurantTestData.*;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.*;
import static ua.restaurant.vote.VoteTestData.MATCHER;
import static ua.restaurant.vote.VoteTestData.*;

/**
 * Created by Galushkin Pavel on 13.03.2017.
 */
public class VoteProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteProfileRestController.REST_URL + '/';

    @Override
    public void setUp() {
        super.setUp();
        voteService.evictCache();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE2_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(VOTE2));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(VOTES_USER)));
    }
    // TODO не работает при запуске мавеном всех тестов. Отдельно сам по себе проходит
    @Test
    @Transactional
    @Rollback
    public void testCreate() throws Exception {
        /*ResultActions action = */mockMvc.perform(post(REST_URL + "restaurant/{restaurantId}", RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(RESTAURANT2_ID)))
                .andExpect(status().isCreated());

        Vote returned = voteService.getVote(USER1_ID, LocalDate.now());//MATCHER.fromJsonAction(action);
        Vote created = VoteTestData.getCreated();
        created.setRestaurant(RESTAURANT2);
        created.setUser(USER1);

        created.setId(100021); //TODO в мавене возвращается индекс 100025 !!

        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(returned, VOTE6, VOTE2), voteService.getAll(USER1_ID));
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().plusMinutes(1));
        Vote expected = voteService.getVote(USER2_ID, LocalDate.now());

        mockMvc.perform(put(REST_URL + "restaurant/{restaurantId}", RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2))
                .content(JsonUtil.writeValue(RESTAURANT1_ID)))
                .andExpect(status().isOk());

        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
        Vote updated = voteService.getVote(USER2_ID, LocalDate.now());
        MATCHER.assertEquals(expected, updated);
        RestaurantTestData.MATCHER.assertEquals(RESTAURANT1, updated.getRestaurant());
    }

    @Test
    @Transactional
    public void testUpdateAfterDeadLine() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().minusMinutes(1));

        mockMvc.perform(put(REST_URL + "restaurant/{restaurantId}", RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(RESTAURANT2_ID)))
                .andExpect(status().is5xxServerError());

        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().plusMinutes(1));
        mockMvc.perform(delete(REST_URL + VOTE1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE5), voteService.getAll(ADMIN_ID));
    }

    @Test
    @Transactional
    public void testDeleteAfterDeadLine() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().minusMinutes(1));
        mockMvc.perform(delete(REST_URL + VOTE1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE5, VOTE1), voteService.getAll(ADMIN_ID));
    }

    @Test
    @Transactional
    public void testDeleteNotFound() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().plusMinutes(1));
        mockMvc.perform(delete(REST_URL + 1)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
    }

    @Test
    public void testGetWithUserForPeriod() throws Exception {
        mockMvc.perform(get(REST_URL + "/between")
                .param("startDate", "2017-01-30")
                .param("endDate", "2017-02-20")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_TO.contentListMatcher(VOTE_TO_JSON_VIEW8, VOTE_TO_JSON_VIEW7))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetWithUserForUndefinedPeriod() throws Exception {
        mockMvc.perform(get(REST_URL + "/between?startDate=&endDate=")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_TO.contentListMatcher(VOTE_TO_JSON_VIEW6, VOTE_TO_JSON_VIEW5))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetResultSet() throws Exception {
        mockMvc.perform(get(REST_URL + "/result?date=2017-01-30")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(ResultTestData.MATCHER.contentListMatcher(ResultTestData.RESULT_TO_LIST))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
