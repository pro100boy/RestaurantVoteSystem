package ua.restaurant.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.restaurant.vote.model.Restaurant;

import java.util.List;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@Transactional(readOnly = true)
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
}
