package ua.restaurant.vote;

import ua.restaurant.vote.matcher.ModelMatcher;
import ua.restaurant.vote.to.ResultTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Galushkin Pavel on 10.03.2017.
 */
public class ResultTestData {
    public static final ModelMatcher<ResultTo> MATCHER = ModelMatcher.of(ResultTo.class);

    public static final ResultTo RESULT_TO1 = new ResultTo(100004, RestaurantTestData.RESTAURANT1.getName(), 1);
    public static final ResultTo RESULT_TO2 = new ResultTo(100005, RestaurantTestData.RESTAURANT2.getName(), 1);
    public static final ResultTo RESULT_TO3 = new ResultTo(100006, RestaurantTestData.RESTAURANT3.getName(), 2);

    public static final List<ResultTo> RESULT_TO_LIST = new ArrayList<>(Arrays.asList(RESULT_TO3, RESULT_TO1, RESULT_TO2));
}
