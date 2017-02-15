import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml"))
        {

            System.out.println("\n>>>>>>>>>  Bean definition names: >>>>>>>>>>");
            Arrays.asList(appCtx.getBeanDefinitionNames()).stream().forEach(System.out::println);
            System.out.println(">>>>>>>>> >>>>>>>>>>\n");

//        UserRepository userRepository = (UserRepository) appCtx.getBean("mockUserRepository");
/*            UserRepository userRepository = appCtx.getBean(UserRepository.class);
            userRepository.getAll();*/
            appCtx.close();
        }
    }
}
