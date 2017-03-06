package ua.restaurant.vote;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.web.user.AdminRestController;

import java.util.Arrays;
import java.util.List;
import static ua.restaurant.vote.TestUtil.mockAuthorize;
import static ua.restaurant.vote.UserTestData.USER1;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
//TODO разобраться зачем этот файл нужен, если нужен вообще
public class SpringMainTest {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/mock.xml"))
        {
            mockAuthorize(USER1);

            System.out.println("\n>>>>>>>>>  Bean definition names: >>>>>>>>>>");
            Arrays.asList(appCtx.getBeanDefinitionNames()).stream().forEach(System.out::println);
            System.out.println(">>>>>>>>> >>>>>>>>>>\n");

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);

            List<User> users = adminUserController.getAll();
            System.out.println(users);

            /*MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);*/
        }
    }
}
