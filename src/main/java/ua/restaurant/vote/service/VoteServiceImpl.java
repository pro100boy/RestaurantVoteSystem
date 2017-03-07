package ua.restaurant.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.repository.UserRepository;
import ua.restaurant.vote.repository.VoteRepository;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.util.Collection;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote get(int id, int userId) throws NotFoundException {
        return null;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {

    }

    @Override
    public Collection<Vote> getAll(int userId) {
        return null;
    }

    @Override
    public Vote update(Vote vote, int userId, int restaurantId) throws NotFoundException {
        return null;
    }

    @Transactional // because select and insert operations must be in one transaction
    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {

        if (!vote.isNew() && get(vote.getId(), userId) == null)
            vote = null;
        else {
            vote.setUser(userRepository.getOne(userId));
            //vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        }
        Assert.notNull(vote, "meal must not be null");
        return voteRepository.save(vote);
    }

    @Override
    public Vote getWithUser(int id, int userId) {
        return null;
    }
}
