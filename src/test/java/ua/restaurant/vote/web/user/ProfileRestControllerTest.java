package ua.restaurant.vote.web.user;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.VoteTestData;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.to.UserTo;
import ua.restaurant.vote.util.UserUtil;
import ua.restaurant.vote.web.AbstractControllerTest;
import ua.restaurant.vote.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.restaurant.vote.TestUtil.userHttpBasic;
import static ua.restaurant.vote.UserTestData.*;
import static ua.restaurant.vote.UserTestData.USER3;
import static ua.restaurant.vote.web.user.ProfileRestController.REST_URL;

/**
 * Created by Galushkin Pavel on 05.03.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileRestControllerTest extends AbstractControllerTest {

    @Override
    public void setUp() {
        super.setUp();
        userService.evictCache();
    }

    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER1)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER2, USER3), userService.getAll());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(UserUtil.updateFromTo(new User(USER1), updatedTo), userService.getByEmail("newemail@ya.ru"));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null);

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "admin@gmail.com", "newPassword");

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isConflict());
    }

    // TODO ERROR ExceptionInfoHandler - Exception at request http://localhost/rest/profile/between, org.springframework.http.converter.HttpMessageNotWritableException: Could not write content: Infinite recursion (StackOverflowError)
    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/between")
                .param("startDate", "2016-01-30").param("endDate", "2018-01-30")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print());
        //VoteTestData.MATCHER.assertCollectionEquals(Arrays.asList(VoteTestData.VOTE6, VoteTestData.VOTE2), USER1.getVotes());
    }
}