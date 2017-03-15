package ua.restaurant.vote;

import ua.restaurant.vote.matcher.ModelMatcher;
import ua.restaurant.vote.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;
import static ua.restaurant.vote.model.BaseEntity.START_SEQ;

/**
 * Created by Galushkin Pavel on 11.03.2017.
 */
public class MenuTestData {
    public static final ModelMatcher<Menu> MATCHER = ModelMatcher.of(Menu.class);

    public static final int MENU1_ID = START_SEQ + 7;
    public static final int MENU2_ID = START_SEQ + 8;
    public static final int MENU3_ID = START_SEQ + 9;
    public static final int MENU4_ID = START_SEQ + 18;
    public static final int MENU5_ID = START_SEQ + 19;
    public static final int MENU6_ID = START_SEQ + 20;

    public static final Menu MENU1 = new Menu(MENU1_ID, of(2017, Month.JANUARY, 30), "Dish 1", 12.5f);
    public static final Menu MENU2 = new Menu(MENU2_ID, of(2017, Month.JANUARY, 30), "Dish 2", 13.5f);
    public static final Menu MENU3 = new Menu(MENU3_ID, of(2017, Month.JANUARY, 30), "Dish 3", 14.5f);
    public static final Menu MENU4 = new Menu(MENU4_ID, of(2017, Month.FEBRUARY, 20), "Dish 4", 120.5f);
    public static final Menu MENU5 = new Menu(MENU5_ID, of(2017, Month.FEBRUARY, 20), "Dish 5", 130.5f);
    public static final Menu MENU6 = new Menu(MENU6_ID, of(2017, Month.FEBRUARY, 20), "Dish 6", 140.5f);

    public static final List<Menu> MENUS = Arrays.asList(MENU1, MENU2, MENU3);

    public static Menu getCreated() {
        return new Menu(null, LocalDate.now(), "Created menu", 5.99f);
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, LocalDate.now(), "Updated menu", 15.99f);
    }
}
