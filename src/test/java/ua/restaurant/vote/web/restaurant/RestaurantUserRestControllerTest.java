package ua.restaurant.vote.web.restaurant;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import ua.restaurant.vote.VoteTestData;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.web.AbstractControllerTest;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.restaurant.vote.RestaurantTestData.MATCHER;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.USER1;
/**
 * Created by Galushkin Pavel on 11.03.2017.
 */
public class RestaurantUserRestControllerTest  extends AbstractControllerTest {
    private static final String REST_URL = RestaurantUserRestController.REST_URL + '/';

    @Override
    public void setUp() {
        super.setUp();
        restaurantService.evictCache();
    }

    @Test
    public void testFindAllForDate() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + "polls")
                .param("date", "2017-01-30")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andDo(print());
        //TODO сделать матчер для меню из выборки
/*        Restaurant returned = MATCHER.fromJsonAction(action);
        VoteTestData.MATCHER.assertCollectionEquals( Arrays.asList(VoteTestData.VOTE8, VoteTestData.VOTE1, VoteTestData.VOTE6,VoteTestData.VOTE5 ),returned.getVotes());*/
    }

    @Test
    public void testFindAllForToday() throws Exception {
        mockMvc.perform(get(REST_URL + "polls?date=")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andDo(print());
    }
}
