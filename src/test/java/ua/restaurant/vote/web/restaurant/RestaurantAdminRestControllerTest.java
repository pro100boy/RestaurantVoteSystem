package ua.restaurant.vote.web.restaurant;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.MenuTestData;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.VoteTestData;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.web.AbstractControllerTest;
import ua.restaurant.vote.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.restaurant.vote.RestaurantTestData.*;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.ADMIN;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestaurantAdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantAdminRestController.REST_URL + '/';

    @Override
    public void setUp() {
        super.setUp();
        restaurantService.evictCache();
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT2, RESTAURANT3), restaurantService.getAll());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Restaurant updated = getUpdated();
        mockMvc.perform(put(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, restaurantService.get(RESTAURANT1_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Restaurant updated = getUpdated();
        updated.setName("");
        mockMvc.perform(put(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Restaurant expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Restaurant returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3, returned), restaurantService.getAll());
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Restaurant expected = new Restaurant(null, null, "");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testUpdateHtmlUnsafe() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT1_ID, "Restaurant 1", "<script>alert(123)</script>");
        mockMvc.perform(put(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testGetBetween() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + RESTAURANT1_ID + "/between")
                .param("startDate", "2017-01-30")
                .param("endDate", "2017-02-20")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Restaurant returned = MATCHER.fromJsonAction(action);
        VoteTestData.MATCHER.assertCollectionEquals(Arrays.asList(VoteTestData.VOTE8, VoteTestData.VOTE1, VoteTestData.VOTE6, VoteTestData.VOTE5), returned.getVotes());
        MenuTestData.MATCHER.assertCollectionEquals(Arrays.asList(MenuTestData.MENU4, MenuTestData.MENU1), returned.getMenus());
    }

    @Test
    public void testGetBetweenAll() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + RESTAURANT1_ID + "/between?startDate=&endDate=")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Restaurant returned = MATCHER.fromJsonAction(action);
        VoteTestData.MATCHER.assertCollectionEquals(Arrays.asList(VoteTestData.VOTE8, VoteTestData.VOTE1, VoteTestData.VOTE6, VoteTestData.VOTE5), returned.getVotes());
        MenuTestData.MATCHER.assertCollectionEquals(Arrays.asList(MenuTestData.MENU4, MenuTestData.MENU1), returned.getMenus());
    }
}
