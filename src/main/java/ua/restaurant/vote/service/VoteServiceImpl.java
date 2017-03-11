package ua.restaurant.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.repository.RestaurantRepository;
import ua.restaurant.vote.repository.UserRepository;
import ua.restaurant.vote.repository.VoteRepository;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.util.DateTimeUtil;
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
        if (!vote.isNew() && getWithUser(vote.getId(), userId) == null)
            vote = null;
        else {
            vote.setUser(userRepository.getOne(userId));
            vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        }
        Assert.notNull(vote, "meal must not be null");
        Vote v = voteRepository.save(vote);
        return v;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    @Override
    public Vote getWithUser(int id, int userId) throws NotFoundException {
        Vote vote = voteRepository.findOne(id);
        return checkNotFoundWithId( (vote != null && vote.getUser().getId() == userId ? vote : null), id);
    }

    @Override
    public List<Vote> getWithUserForPeriod(int userId, LocalDate startDate, LocalDate endDate) {
        return voteRepository.getWithUserForPeriod(userId, startDate, endDate);
    }

    @Override
    public List<Vote> getWithRestaurantForPeriod(int restaurantId, LocalDate startDate, LocalDate endDate) {
        return voteRepository.getWithRestaurantForPeriod(restaurantId, startDate, endDate);
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
    public List<VoteTo> getResultSet(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return voteRepository.getResultSet(date);
    }
}
