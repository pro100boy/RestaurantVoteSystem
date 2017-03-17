package ua.restaurant.vote.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.ResultTo;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
@Transactional(readOnly = true)
@SuppressWarnings("JpaQlInspection")
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Override
    Vote save(Vote vote);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    Vote findOne(Integer id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 AND v.date = ?2")
    Vote getVote(int userId, LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 ORDER BY v.date DESC")
    List<Vote> getAll(int userId);

    //ORDERED date
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:userId AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC, v.id DESC")
    List<Vote> getWithUserForPeriod(@Param("userId") int userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //ORDERED date
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.restaurant.id=:restId AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC, v.id DESC")
    List<Vote> getWithRestaurantForPeriod(@Param("restId") int restId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(name = "getResultTo")
    List<ResultTo> getResultSet(@Param("date") LocalDate date);
}
