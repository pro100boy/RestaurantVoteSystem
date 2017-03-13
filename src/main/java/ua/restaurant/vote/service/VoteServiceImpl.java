package ua.restaurant.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.repository.RestaurantRepository;
import ua.restaurant.vote.repository.UserRepository;
import ua.restaurant.vote.repository.VoteRepository;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.util.DateTimeUtil;
import ua.restaurant.vote.util.VoteUtil;
import ua.restaurant.vote.util.exception.NotFoundException;
import ua.restaurant.vote.util.exception.VoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ua.restaurant.vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // because select and insert operations must be in one transaction
    @Transactional
    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null)
            vote = null;
        else {
            vote.setUser(userRepository.getOne(userId));
            vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        }
        Assert.notNull(vote, "meal must not be null");
        return voteRepository.save(vote);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    @Cacheable("votes")
    @Override
    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    @Override
    public Vote get(int id, int userId) throws NotFoundException {
        Vote vote = voteRepository.findOne(id);
        return checkNotFoundWithId( (vote != null && vote.getUser().getId() == userId ? vote : null), id);
    }

    @Cacheable("votes")
    @Override
    public List<VoteTo> getWithUserForPeriod(int userId, LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        return VoteUtil.asToList(voteRepository.getWithUserForPeriod(userId, startDate, endDate), true);
    }

    @Cacheable("votes")
    @Override
    public List<VoteTo> getWithRestaurantForPeriod(int restaurantId, LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        return VoteUtil.asToList(voteRepository.getWithUserForPeriod(restaurantId, startDate, endDate), false);
    }

    @Override
    @Transactional
    public Vote update(Vote vote, int userId, int restaurantId) throws NotFoundException {
        if (LocalTime.now().isAfter(DateTimeUtil.getDeadlineVoteTime())) {
            throw new VoteException("It's too late for vote. Vote can't be saved");
        }
        Assert.notNull(vote, "vote must not be null");
        return checkNotFoundWithId(save(vote, userId, restaurantId), vote.getId());
    }

    @Override
    public List<ResultTo> getResultSet(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return voteRepository.getResultSet(date);
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Override
    public void evictCache() {
    }
}
