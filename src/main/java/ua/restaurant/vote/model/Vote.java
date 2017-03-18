package ua.restaurant.vote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.format.annotation.DateTimeFormat;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.util.DateTimeUtil;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
@SqlResultSetMapping(
        name = "ResultToMapping",
        classes = @ConstructorResult(
                targetClass = ResultTo.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "cnt", type = Integer.class)}))

@NamedNativeQueries({
        @NamedNativeQuery(name = "getResultTo", query =
                "SELECT r.ID, r.NAME, COUNT(v.REST_ID) AS cnt FROM RESTAURANTS r LEFT JOIN VOTES v ON r.ID=v.REST_ID\n" +
                        "WHERE v.VOTE_DATE = :date OR v.VOTE_DATE IS NULL\n" +
                        "GROUP BY r.ID, r.NAME ORDER BY cnt DESC", resultSetMapping = "ResultToMapping")
})

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "user_date_unique_idx")})
public class Vote extends BaseEntity{
    @Column(name = "vote_date", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value="user-votes")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value="restaurant-votes")
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(LocalDate date) {
        this(null, date);
    }

    public Vote(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate dateTime) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + getId() +
                ", date=" + date +
                //", user=" + user +
                //", restaurant=" + restaurant +
                '}';
    }
}
