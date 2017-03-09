package ua.restaurant.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Vote;

import java.util.List;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Override
    Vote save(Vote vote);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    Vote findOne(Integer id);

    //ORDERED date
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.user.id=?1 ORDER BY v.date DESC")
    List<Vote> getAllWithUser(int userId);

/*
    //ORDERED date
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.restaurant.id=?1")
    List<Vote> getAllWithRestaurants(int restId);


    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id = ?1 and v.user.id = ?2")
    Vote getWithUser(int id, int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.id = ?1 and v.restaurant.id = ?2")
    Vote getWithRestaurant(int id, int restId);*/

    /*
    Vote getWithFields(int id, int userId);

    Vote getWithoutUser(int id, int userId);

    Collection<Vote> getAllVotesForAllUsers();
     */
}
