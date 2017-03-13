package ua.restaurant.vote.web.vote;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.util.VoteUtil;
import ua.restaurant.vote.web.AbstractControllerTest;
import ua.restaurant.vote.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.*;
import static ua.restaurant.vote.VoteTestData.MATCHER;
import static ua.restaurant.vote.VoteTestData.*;
import static ua.restaurant.vote.util.VoteUtil.createFromTo;

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
        VoteTo createdTo = getCreatedTo();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        Vote returned = MATCHER.fromJsonAction(action);
        Vote created = createFromTo(createdTo);
        created.setId(returned.getId());

        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(returned, VOTE6, VOTE2), voteService.getAll(USER1_ID));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        VoteTo createdTo = getCreatedTo();
        createdTo.setDate(null);

        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        VoteTo updatedTo = getUpdatedTo(); //new VoteTo(VOTE1_ID, VOTE1.getDate(), RESTAURANT1_ID + 1)
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().plusMinutes(1));
        mockMvc.perform(put(REST_URL + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isOk());
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
        Vote updated = VoteUtil.updateFromTo(VOTE1, updatedTo);
        MATCHER.assertEquals(updated, voteService.get(VOTE1_ID, ADMIN_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        VoteTo updatedTo = new VoteTo(VOTE1_ID, null, ADMIN_ID);
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().plusMinutes(1));
        mockMvc.perform(put(REST_URL + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
    }

    @Test
    public void testUpdateAfterDeadLine() throws Exception {
        VoteTo updatedTo = getUpdatedTo(); //new VoteTo(VOTE1_ID, VOTE1.getDate(), RESTAURANT1_ID + 1)
        DateTimeUtil.setDeadlineVoteTime(LocalTime.now().minusMinutes(1));
        mockMvc.perform(put(REST_URL + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().is5xxServerError());
        DateTimeUtil.setDeadlineVoteTime(DateTimeUtil.DEFAULT_VOTE_DEADLINE_TIME);
        Vote updated = VoteUtil.updateFromTo(VOTE1, updatedTo);
        MATCHER.assertEquals(updated, voteService.get(VOTE1_ID, ADMIN_ID));
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
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetWithUserForUndefinedPeriod() throws Exception {
        mockMvc.perform(get(REST_URL + "/between?startDate=&endDate=")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetResultSet() throws Exception {
        mockMvc.perform(get(REST_URL + "/result?date=2017-01-30")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
