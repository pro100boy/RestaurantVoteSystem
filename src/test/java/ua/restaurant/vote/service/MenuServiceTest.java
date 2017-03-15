package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.model.Menu;
import ua.restaurant.vote.repository.JpaUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static ua.restaurant.vote.MenuTestData.*;
import static ua.restaurant.vote.RestaurantTestData.RESTAURANT1_ID;

public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testValidation() throws Exception {
        // empty datetime
        validateRootCause(() -> service.save(new Menu(null, null, null), RESTAURANT1_ID), ConstraintViolationException.class);
    }

    @Test
    public void testSave() throws Exception {
        Menu created = getCreated();
        service.save(created, RESTAURANT1_ID);
        MATCHER.assertCollectionEquals(
                Arrays.asList(created, MENU4, MENU1),
                service.getAllWithRestaurant(RESTAURANT1_ID));
    }

   @Test
    public void testDelete() throws Exception {
        service.delete(MENU1_ID, RESTAURANT1_ID);
        MATCHER.assertCollectionEquals(
                Arrays.asList(MENU4),
                service.getAllWithRestaurant(RESTAURANT1_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, 1);
    }

    @Test
    public void testGet() throws Exception {
        Menu menu = service.get(MENU1_ID, RESTAURANT1_ID);
        MATCHER.assertEquals(MENU1, menu);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, 1);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MENU4, MENU1), service.getAllWithRestaurant(RESTAURANT1_ID));
    }

    @Test
    public void testGetAllBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MENU1), service.getAllWithRestaurant(RESTAURANT1_ID, LocalDate.of(2017, Month.JANUARY, 1), LocalDate.of(2017, Month.JANUARY, 31)));
    }

    @Test
    public void testUpdate() throws Exception {
        Menu updated = getUpdated();
        service.update(updated, RESTAURANT1_ID);
        MATCHER.assertEquals(updated, service.get(MENU1_ID, RESTAURANT1_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Menu updated = getUpdated();
        MATCHER.assertEquals(updated, service.get(MENU2_ID, RESTAURANT1_ID));
    }


}
