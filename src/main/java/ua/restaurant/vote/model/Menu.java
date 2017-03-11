package ua.restaurant.vote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ua.restaurant.vote.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "menu_date"}, name = "date_restaurant_unique_idx")})
public class Menu extends NamedEntity {
    @Column(name = "menu_date", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate date;

    @Column(name = "price", nullable = false, columnDefinition = "real default 0")
    @NotNull
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value="restaurant-menus")
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(LocalDate date, String name, Float price) {
        this(null, date, name, price);
    }

    public Menu(Integer id, LocalDate date, String name, Float price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + getId() +
                ", date=" + date +
                ", dish=" + name +
                ", price=" + Float.toString(price) +
                //", restaurant=" + restaurant +
                '}';
    }
}
