package ua.restaurant.vote.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.restaurant.vote.MenuTestData;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.web.AbstractControllerTest;
import ua.restaurant.vote.web.json.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.restaurant.vote.RestaurantTestData.*;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT3;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.ADMIN;
import static ua.restaurant.vote.UserTestData.USER1;

/**
 * Created by Galushkin Pavel on 11.03.2017.
 */
public class RestaurantProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantProfileRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(RESTAURANT1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetByName() throws Exception {
        mockMvc.perform(get(REST_URL + "by?name=" + RESTAURANT1.getName())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(RESTAURANT1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(RESTAURANT1, RESTAURANT2, RESTAURANT3)));
    }

    @Test
    public void testFindAllForDate() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + "polls")
                .param("date", "2017-01-30")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andDo(print());
        List<Restaurant> returned = JsonUtil.readValues(TestUtil.getContent(action), Restaurant.class);
        MenuTestData.MATCHER.assertCollectionEquals(
                MenuTestData.MENUS,
                returned.stream().flatMap(m -> m.getMenus().stream()).collect(Collectors.toList())
        );
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
