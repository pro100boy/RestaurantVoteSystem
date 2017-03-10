package ua.restaurant.vote.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@Transactional(readOnly = true)
@SuppressWarnings("JpaQlInspection")
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    //List<Restaurant> findAllByOrderByNameAsc();
    List<Restaurant> findAll();

    @Override
    Restaurant findOne(Integer integer);

    Restaurant getByName(String name);

    @EntityGraph(value = Restaurant.GRAPH_WITH_VOTES_MENUS)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    Restaurant getWithVotes(int id);

    @EntityGraph(value = Restaurant.GRAPH_WITH_VOTES_MENUS)
    @Query("SELECT r FROM Restaurant r INNER JOIN FETCH r.votes v WHERE r.id=:id AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC")
    Restaurant getWithVotesForPeriod(@Param("id") int id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /*@Query("SELECT v FROM Vote v JOIN FETCH v.menu WHERE v.id =:id and v.user.id =:userId")
    Restaurant getWithMenu(@Param("id")Integer id, @Param("userId")Integer userId);*/
    @Query(nativeQuery = true, value = "SELECT r.ID, r.NAME, COUNT(v.REST_ID) AS Votes FROM RESTAURANTS r LEFT JOIN VOTES v ON r.ID=v.REST_ID\n" +
            "WHERE v.VOTE_DATE BETWEEN :startDate AND :endDate OR v.VOTE_DATE IS NULL\n" +
            "GROUP BY r.ID, r.NAME")
    List<Object[]> getWithMenu(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
