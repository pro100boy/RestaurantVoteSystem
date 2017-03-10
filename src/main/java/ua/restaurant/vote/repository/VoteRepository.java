package ua.restaurant.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Vote;

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

    //ORDERED date
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.user.id=:id AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC, v.id DESC")
    List<Vote> getWithUserForPeriod(@Param("id") int userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    //ORDERED date
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.restaurant.id=?1 AND v.date BETWEEN ?2 AND ?3 ORDER BY v.date DESC, v.id DESC")
    List<Vote> getWithRestaurantForPeriod(int restId, LocalDate startDate, LocalDate endDate);

}
