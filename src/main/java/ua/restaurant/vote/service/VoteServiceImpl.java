package ua.restaurant.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.repository.RestaurantRepository;
import ua.restaurant.vote.repository.UserRepository;
import ua.restaurant.vote.repository.VoteRepository;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ua.restaurant.vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@Service("voteService")
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
    @CacheEvict(value = {"votes","restaurants","users"}, allEntries = true)
    public Vote save(int userId, int restaurantId) {
        Vote vote = new Vote(LocalDate.now());
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        return checkNotFoundWithId(voteRepository.save(vote), vote.getId());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"votes","restaurants","users"}, allEntries = true)
    public Vote update(int userId, int restaurantId) throws NotFoundException {
        Vote vote = voteRepository.getVote(userId, LocalDate.now());
        if (vote == null) throw new NotFoundException("vote not found");
        if (!vote.isNew() && get(vote.getId(), userId) != null){
            vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        }
        return checkNotFoundWithId(voteRepository.save(vote), vote.getId());
    }

    @Override
    @CacheEvict(value = {"votes","restaurants","users"}, allEntries = true)
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    @Override
    public Vote getVote(int userId, LocalDate date) {
        Vote vote = voteRepository.getVote(userId, date);
        Assert.notNull(vote, "vote must not be null");
        return vote;
    }

    @Cacheable("votes")
    @Override
    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    @Override
    public Vote get(int id, int userId) throws NotFoundException {
        Vote vote = voteRepository.findOne(id);
        return checkNotFoundWithId((vote != null && vote.getUser().getId() == userId ? vote : null), id);
    }

    @Cacheable("votes")
    @Override
    public List<Vote> getWithUserForPeriod(int userId, LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        return voteRepository.getWithUserForPeriod(userId, startDate, endDate);
    }

    @Cacheable("votes")
    @Override
    public List<Vote> getWithRestaurantForPeriod(int restaurantId, LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        return voteRepository.getWithRestaurantForPeriod(restaurantId, startDate, endDate);
    }

    @Override
    public List<ResultTo> getResultSet(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return voteRepository.getResultSet(date);
    }

    @CacheEvict(value = {"votes","restaurants","users"}, allEntries = true)
    @Override
    public void evictCache() {
    }
}
