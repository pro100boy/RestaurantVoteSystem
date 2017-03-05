package ua.restaurant.vote.to;

import ua.restaurant.vote.HasId;

/**
 * Created by Galushkin Pavel on 05.03.2017.
 */
public class BaseTo implements HasId {
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}