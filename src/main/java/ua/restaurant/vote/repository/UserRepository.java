package ua.restaurant.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Galushkin Pavel on 04.03.2017.
 */
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Override
    User save(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    User findOne(Integer id);

    //@Override
    //@Query("SELECT u FROM User u ORDER BY u.name, u.email")
    List<User> findAllByOrderByNameAscEmailAsc();

    // null if not found
    User getByEmail(String email);

    @EntityGraph(value = User.GRAPH_WITH_VOTES)
    @Query("SELECT u FROM User u WHERE u.id=?1")
    User getWithVotes(int id);

    /* USER's votes for ALL RESTAURANTS for PERIOD */
    /*
    SELECT v.VOTE_DATE, r.NAME FROM RESTAURANTS r
    INNER JOIN VOTES v ON r.ID = v.REST_ID INNER JOIN USERS u ON v.USER_ID = u.ID
    WHERE u.ID=100003 AND v.VOTE_DATE BETWEEN '2016-02-20' AND '2018-02-20'
    */
    @EntityGraph(value = User.GRAPH_WITH_VOTES)
    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT u FROM User u LEFT JOIN u.votes v WHERE u.id=:id AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC")
    User getVotesForAllRestaurants(@Param("id") int id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
