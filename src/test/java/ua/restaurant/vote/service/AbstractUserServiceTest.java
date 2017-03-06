package ua.restaurant.vote.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ua.restaurant.vote.model.Role;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ua.restaurant.vote.UserTestData.*;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, USER1, USER2, USER3), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User(null, "Duplicate", "user@ya.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER2, USER3), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<User> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER1, USER2, USER3), all);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        //updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(USER1_ID));
    }

    @Test
    public void testSetEnabledEquals() {
        service.enable(USER1_ID, false);
        Assert.assertFalse(service.get(USER1_ID).isEnabled());
        service.enable(USER1_ID, true);
        Assert.assertTrue(service.get(USER1_ID).isEnabled());
    }
}