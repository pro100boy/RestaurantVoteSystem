package ua.restaurant.vote.util;

import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
public class VoteUtil {
    private VoteUtil() {
    }

    private static VoteTo asToWithRest(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getName());
    }

    private static VoteTo asToWithUser(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate(), vote.getUser().getName());
    }

    public static List<VoteTo> asToList(List<Vote> voteList, boolean isGetWithUserForPeriod) {
        return (isGetWithUserForPeriod ?
                voteList.stream().map(r -> VoteUtil.asToWithRest(r)).collect(Collectors.toList()) :
                voteList.stream().map(r -> VoteUtil.asToWithUser(r)).collect(Collectors.toList())
        );
    }
}
