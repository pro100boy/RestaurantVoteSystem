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

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@Transactional(readOnly = true)
@SuppressWarnings("JpaQlInspection")
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Override
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    //List<Restaurant> findAllByOrderByNameAsc();
    List<Restaurant> findAll();

    // Each restaurant provides new menu each day
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.votes v LEFT JOIN FETCH r.menus m " +
            "WHERE v.date = ?1 AND m.date = ?1 ORDER BY r.name")
    List<Restaurant> findAllForDate(LocalDate date);

    @Override
    Restaurant findOne(Integer integer);

    List<Restaurant> findByNameIgnoreCaseStartingWith(String name);

    @EntityGraph(value = Restaurant.GRAPH_WITH_VOTES_MENUS)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.votes v WHERE r.id=:id AND v.date BETWEEN :startDate AND :endDate")
    Restaurant getWithParamsForPeriod(@Param("id") int id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
