import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml"))
        {
           /* System.out.println("\n>>>>>>>>>  Bean definition names: >>>>>>>>>>");
            Arrays.asList(appCtx.getBeanDefinitionNames()).stream().forEach(System.out::println);
            System.out.println(">>>>>>>>> >>>>>>>>>>\n");*/

            UserService userService = appCtx.getBean(UserService.class);
            userService.getAll().stream().forEach(System.out::println);
        }
    }
}
