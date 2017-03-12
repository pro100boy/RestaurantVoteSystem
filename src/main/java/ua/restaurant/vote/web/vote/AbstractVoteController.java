package ua.restaurant.vote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.AuthorizedUser;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.service.VoteService;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ua.restaurant.vote.util.ValidationUtil.checkIdConsistent;
import static ua.restaurant.vote.util.ValidationUtil.checkNew;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
public class AbstractVoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService service;

    public List<Vote> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll for User {}", userId);
        return service.getAll(userId);
    }

    public Vote get(int id) {
        int userId = AuthorizedUser.id();
        log.info("get vote {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete vote {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public Vote create(Vote vote, int restaurantId) {
        checkNew(vote);
        int userId = AuthorizedUser.id();
        log.info("create {} for User {} and Restaurant {}", vote, userId, restaurantId);
        return service.save(vote, userId, restaurantId);
    }

    public void update(Vote vote, int id, int restaurantId) {
        checkIdConsistent(vote, id);
        int userId = AuthorizedUser.id();
        log.info("update {} for User {} and Restaurant {}", vote, userId, restaurantId);
        service.update(vote, userId, restaurantId);
    }

    public List<VoteTo> getWithRestaurantForPeriod(int restaurantId, LocalDate startDate, LocalDate endDate) {
        log.info("getWithRestaurantForPeriod for restaurant {} between {} and {}", restaurantId, startDate, endDate);
        return service.getWithRestaurantForPeriod(restaurantId,
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE);
    }

    public List<VoteTo> getWithUserForPeriod(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getWithUserForPeriod for user {} between {} and {}", userId, startDate, endDate);
        return service.getWithUserForPeriod(userId,
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE);
    }

    public List<ResultTo> getResultSet(LocalDate date){
        log.info("get vote result for date {}", date);
        return service.getResultSet(date);
    }
}
