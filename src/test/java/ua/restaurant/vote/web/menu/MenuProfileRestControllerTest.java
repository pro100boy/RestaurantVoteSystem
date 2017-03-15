package ua.restaurant.vote.web.menu;

import org.junit.Test;
import org.springframework.http.MediaType;
import ua.restaurant.vote.web.AbstractControllerTest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.restaurant.vote.MenuTestData.*;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT2_ID;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.USER1;

public class MenuProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuProfileRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MENU2_ID, RESTAURANT2_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MENU2));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1,RESTAURANT2_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
    @Test
    public void testGetToday() throws Exception {
        mockMvc.perform(get(REST_URL + "today", RESTAURANT2_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }
}
