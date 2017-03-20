package ua.restaurant.vote.web.vote;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VoteProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteProfileRestController.REST_URL + '/';

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

    @Test
    @Transactional
    public void testCreate() throws Exception {
        mockMvc.perform(post(REST_URL + "restaurants/{restaurantId}", RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(RESTAURANT2_ID)))
                .andExpect(status().isCreated());

        Vote returned = voteService.getVote(USER1_ID, LocalDate.now());

        Vote created = VoteTestData.getCreated();
        created.setRestaurant(RESTAURANT2);
        created.setUser(USER1);
        created.setId(100023);

        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(returned, VOTE6, VOTE2), voteService.getAll(USER1_ID));
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().plusMinutes(1));
        Vote expected = voteService.getVote(USER2_ID, LocalDate.now());

        mockMvc.perform(put(REST_URL + "restaurants/{restaurantId}", RESTAURANT1_ID)
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
    public void testUpdateAfterDeadLine() throws Exception {
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().minusMinutes(1));

        mockMvc.perform(put(REST_URL + "restaurants/{restaurantId}", RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(RESTAURANT2_ID)))
                .andExpect(status().is5xxServerError());

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
