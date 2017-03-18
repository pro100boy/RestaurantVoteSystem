package ua.restaurant.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.restaurant.vote.model.Restaurant;
import ua.restaurant.vote.repository.RestaurantRepository;
import ua.restaurant.vote.to.RestaurantTo;
import ua.restaurant.vote.util.RestaurantUtil;
import ua.restaurant.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ua.restaurant.vote.util.ValidationUtil.checkNotFound;
import static ua.restaurant.vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Galushkin Pavel on 06.03.2017.
 */
@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository repository;

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public List<Restaurant> getByName(String name) throws NotFoundException {
        Assert.notNull(name, "name must not be null");
        return checkNotFound(repository.findByNameIgnoreCaseStartingWith(name), "name=" + name);
    }

    @Cacheable("restaurants")
    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void evictCache() {}

    @Override
    public List<RestaurantTo> findAllForDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return RestaurantUtil.asToList(repository.findAllForDate(date));
    }

    @Cacheable("restaurants")
    @Override
    public Restaurant getWithParamsForPeriod(int id, LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");
        return repository.getWithParamsForPeriod(id, startDate, endDate);
    }
}
