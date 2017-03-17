package ua.restaurant.vote.web.menu;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Menu;
import ua.restaurant.vote.web.AbstractControllerTest;
import ua.restaurant.vote.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.restaurant.vote.MenuTestData.*;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1_ID;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.ADMIN;

public class MenuAdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuAdminRestController.REST_URL + '/';

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL, RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(MENU4, MENU1));
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL, RESTAURANT1_ID)
                .param("startDate", "2017-01-01")
                .param("endDate", "2018-01-01")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(MENU4, MENU1));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MENU1_ID, RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MENU4), menuService.getAllWithRestaurant(RESTAURANT1_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1, RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Menu updated = getUpdated();
        mockMvc.perform(put(REST_URL + MENU1_ID, RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionEquals(Arrays.asList(updated, MENU4), menuService.getAllWithRestaurant(RESTAURANT1_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Menu updated = getUpdated();
        updated.setName("");
        mockMvc.perform(put(REST_URL + MENU1_ID, RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Menu expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL, RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Menu returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MENU4, MENU1), menuService.getAllWithRestaurant(RESTAURANT1_ID));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Menu invalid = new Menu(null, null, null);
        mockMvc.perform(post(REST_URL, RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}