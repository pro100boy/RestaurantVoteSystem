package ua.restaurant.vote;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.service.UserService;
import ua.restaurant.vote.web.user.AdminRestController;

import java.util.Arrays;
import java.util.List;
import static ua.restaurant.vote.TestUtil.mockAuthorize;
import static ua.restaurant.vote.UserTestData.ADMIN;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
//TODO разобраться зачем этот файл нужен, если нужен вообще
public class SpringMainTest {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml"))
        {
            mockAuthorize(ADMIN);

            /*System.out.println("\n>>>>>>>>>  Bean definition names: >>>>>>>>>>");
            Arrays.asList(appCtx.getBeanDefinitionNames()).stream().forEach(System.out::println);
            System.out.println(">>>>>>>>> >>>>>>>>>>\n");*/

            UserService userService = appCtx.getBean(UserService.class);
            TestUtil.prntCollect(userService.getAll());
        }
    }
}
