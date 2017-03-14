package ua.restaurant.vote.web.vote;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.web.AbstractControllerTest;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1_ID;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.ADMIN;
import static ua.restaurant.vote.UserTestData.USER1_ID;
import static ua.restaurant.vote.VoteTestData.*;

/**
 * Created by Galushkin Pavel on 13.03.2017.
 */
public class VoteAdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteAdminRestController.REST_URL + '/';

    @Override
    public void setUp() {
        super.setUp();
        voteService.evictCache();
    }

    /*
    ua.restaurant.vote.web.vote.VoteAdminRestController#delete
    ua.restaurant.vote.web.vote.VoteAdminRestController#getAll
    ua.restaurant.vote.web.vote.VoteAdminRestController#getWithUserForPeriod
    ua.restaurant.vote.web.vote.VoteAdminRestController#getWithRestaurantForPeriod
     */

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE2_ID + "/users/" + USER1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(VOTE6), voteService.getAll(USER1_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL + "users/" + USER1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MATCHER.contentListMatcher(VOTE6, VOTE2));
    }

    @Test
    public void testGetWithUserForPeriod() throws Exception {
        mockMvc.perform(get(REST_URL + "users/" + USER1_ID + "/between")
                .param("startDate", "2017-01-30")
                .param("endDate", "2017-02-20")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_TO.contentListMatcher(VOTE_TO_JSON_VIEW6, VOTE_TO_JSON_VIEW5))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetWithRestaurantForPeriod() throws Exception {
        mockMvc.perform(get(REST_URL + "restaurants/" + RESTAURANT1_ID + "/between")
                .param("startDate", "2017-01-30")
                .param("endDate", "2017-02-20")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_TO.contentListMatcher(VOTE_TO_JSON_VIEWS))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
