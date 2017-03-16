package ua.restaurant.vote.util;

import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.VoteToJSONView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
public class VoteUtil {
    private VoteUtil() {
    }

    private static VoteToJSONView asToWithRest(Vote vote) {
        return new VoteToJSONView(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getRestaurant().getName());
    }

    private static VoteToJSONView asToWithUser(Vote vote) {
        return new VoteToJSONView(vote.getId(), vote.getDate(), vote.getUser().getId(), vote.getUser().getName());
    }

    public static VoteToJSONView fromVote(Vote vote, User user)
    {
        return new VoteToJSONView(vote.getId(), vote.getDate(), user.getId(), user.getName());
    }

    public static VoteToJSONView fromVote(Vote vote, Restaurant rest)
    {
        return new VoteToJSONView(vote.getId(), vote.getDate(), rest.getId(), rest.getName());
    }

    public static List<VoteToJSONView> asToList(List<Vote> voteList, boolean isGetWithUserForPeriod) {
        return (isGetWithUserForPeriod ?
                voteList.stream().map(r -> VoteUtil.asToWithRest(r)).collect(Collectors.toList()) :
                voteList.stream().map(r -> VoteUtil.asToWithUser(r)).collect(Collectors.toList())
        );
    }
}
