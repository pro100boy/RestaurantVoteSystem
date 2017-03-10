package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.util.Arrays;

import static ua.restaurant.vote.RestaurantTestData.*;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
public abstract class AbstractRestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    RestaurantService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testSave() throws Exception {
        Restaurant newRest = getCreated();
        Restaurant created = service.save(newRest);
        newRest.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3, created), service.getAll());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(RESTAURANT1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT2, RESTAURANT3), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        Restaurant rest = service.get(RESTAURANT1_ID);
        MATCHER.assertEquals(RESTAURANT1, rest);
    }

    @Test
    public void testGetByName() throws Exception {
        Restaurant rest = service.getByName("Restaurant 1");
        MATCHER.assertEquals(RESTAURANT1, rest);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(RESTAURANTS, service.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = getUpdated();
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(RESTAURANT1_ID));
    }
}
