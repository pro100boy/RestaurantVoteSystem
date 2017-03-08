package ua.restaurant.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Vote;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
/*
    @Override
    Vote save(Vote vote);

    Vote getWithUser(int id, int userId);*/
}
