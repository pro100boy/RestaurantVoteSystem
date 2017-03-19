package ua.restaurant.vote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.AuthorizedUser;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.service.VoteService;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.to.VoteToJSONView;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.util.VoteUtil;
import ua.restaurant.vote.util.exception.VoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public List<Vote> getAll(int userId) {
        log.info("getAllWithUser for User {}", userId);
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

    public Vote create(int restaurantId) {
        int userId = AuthorizedUser.id();
        log.info("create vote for User {} and Restaurant {}", AuthorizedUser.get(), restaurantId);
        return service.save(userId, restaurantId);
    }

    public void update(int restaurantId) {
        int userId = AuthorizedUser.id();
        log.info("update vote for User {} and Restaurant {}", AuthorizedUser.get(), restaurantId);
        service.update(userId, restaurantId);
    }

    public List<VoteToJSONView> getWithRestaurantForPeriod(int restaurantId, LocalDate startDate, LocalDate endDate) {
        log.info("getWithRestaurantForPeriod for restaurant {} between {} and {}", restaurantId, startDate, endDate);
        return VoteUtil.asToList(service.getWithRestaurantForPeriod(restaurantId,
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE), false);
    }

    public List<VoteToJSONView> getWithUserForPeriod(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getWithUserForPeriod for user {} between {} and {}", userId, startDate, endDate);
        return VoteUtil.asToList(service.getWithUserForPeriod(userId,
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE), true);
    }

    public List<ResultTo> getResultSet(LocalDate date) {
        if (date == null) date = LocalDate.now();
        log.info("get vote result for date {}", date);
        return service.getResultSet(date);
    }
}
