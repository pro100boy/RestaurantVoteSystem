package ua.restaurant.vote.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ua.restaurant.vote.repository.JpaUtil;
import ua.restaurant.vote.repository.VoteRepository;
import ua.restaurant.vote.service.MenuService;
import ua.restaurant.vote.service.RestaurantService;
import ua.restaurant.vote.service.UserService;
import ua.restaurant.vote.service.VoteService;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * Created by Galushkin Pavel on 05.03.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
abstract public class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @Autowired
    private JpaUtil jpaUtil;

    protected MockMvc mockMvc;

    @Autowired
    protected UserService userService;

    @Autowired
    protected VoteRepository voteRepository;
    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @Before
    public void setUp() {
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
