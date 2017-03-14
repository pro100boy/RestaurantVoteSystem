package ua.restaurant.vote.to;

import org.springframework.format.annotation.DateTimeFormat;
import ua.restaurant.vote.util.DateTimeUtil;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
public class VoteTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate date;

    @NotNull
    private Integer objId;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDate date, Integer objId) {
        super(id);
        this.date = date;
        this.objId = objId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestaurantId() {
        return objId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.objId = restaurantId;
    }

    public Integer getObjId() {
        return objId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + getId() +
                ", date=" + date +
                ", objId=" + objId +
                '}';
    }
}
