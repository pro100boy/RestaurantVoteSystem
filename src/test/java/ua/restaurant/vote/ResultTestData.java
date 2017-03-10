package ua.restaurant.vote;

import ua.restaurant.vote.matcher.ModelMatcher;
import ua.restaurant.vote.to.VoteTo;

import ua.restaurant.vote.RestaurantTestData.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Galushkin Pavel on 10.03.2017.
 */
public class ResultTestData {
    public static final ModelMatcher<VoteTo> MATCHER = ModelMatcher.of(VoteTo.class);

    public static final VoteTo VOTE_TO1 = new VoteTo(100004, RestaurantTestData.RESTAURANT1.getName(), 4);
    public static final VoteTo VOTE_TO2 = new VoteTo(100005, RestaurantTestData.RESTAURANT2.getName(), 2);
    public static final VoteTo VOTE_TO3 = new VoteTo(100006, RestaurantTestData.RESTAURANT3.getName(), 2);

    public static final List<VoteTo> VOTE_TO_LIST = new ArrayList<>(Arrays.asList(VOTE_TO1, VOTE_TO2, VOTE_TO3));
}
