package ua.restaurant.vote.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.AuthorizedUser;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.service.UserService;
import ua.restaurant.vote.to.UserTo;
import ua.restaurant.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ua.restaurant.vote.util.ValidationUtil.checkIdConsistent;
import static ua.restaurant.vote.util.ValidationUtil.checkNew;

/**
 * Created by Galushkin Pavel on 05.03.2017.
 */
public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get " + id);
        return service.get(id);
    }

    public User create(User user) {
        checkNew(user);
        log.info("create " + user);
        return service.save(user);
    }

    public void delete(int id) {
        log.info("delete " + id);
        service.delete(id);
    }

    public void update(User user, int id) {
        checkIdConsistent(user, id);
        log.info("update " + user);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("update " + userTo);
        checkIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User getByMail(String email) {
        log.info("getByEmail " + email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }

    public User getBetween(int id, LocalDate startDate, LocalDate endDate)
    {
        log.info("getWithVotesForPeriod between dates {} - {} for User {}", startDate, endDate, AuthorizedUser.get());
        return service.getWithVotesForPeriod(id,
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE);
    }
}
