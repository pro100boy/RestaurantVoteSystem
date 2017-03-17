package ua.restaurant.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Menu;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 14.03.2017.
 */
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Override
    Menu save(Menu menu);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Override
    Menu findOne(Integer id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=?1 ORDER BY m.date DESC")
    List<Menu> getAllWithRestaurant(int restaurantId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId AND m.date BETWEEN :startDate AND :endDate ORDER BY m.date DESC")
    List<Menu> getAllBetweenDates(@Param("restaurantId") int restaurantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}