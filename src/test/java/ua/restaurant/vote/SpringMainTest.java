package ua.restaurant.vote;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.restaurant.vote.service.RestaurantService;
import ua.restaurant.vote.service.UserService;

import static ua.restaurant.vote.TestUtil.mockAuthorize;
import static ua.restaurant.vote.UserTestData.ADMIN;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
public class SpringMainTest {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml"))
        {
            mockAuthorize(ADMIN);

            UserService userService = appCtx.getBean(UserService.class);
            TestUtil.prntCollect(userService.getAll());

            RestaurantService restaurantService = (RestaurantService)appCtx.getBean("restaurantService");
            TestUtil.prntCollect(restaurantService.getAll());
        }
    }
}
