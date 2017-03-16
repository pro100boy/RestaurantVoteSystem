package ua.restaurant.vote.to;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;
import ua.restaurant.vote.util.DateTimeUtil;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by Galushkin Pavel on 14.03.2017.
 */
public class VoteToJSONView extends BaseTo {
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate date;

    @NotNull
    private Integer objId;

    @NotBlank // (проверка на символы, кроме пробелов)
    @Column(name = "name", nullable = false)
    @Size(min = 1, max = 255)
    @SafeHtml
    private String objName;

    public VoteToJSONView() {
    }

    public VoteToJSONView(Integer id, LocalDate date, Integer objId, String objName) {
        super(id);
        this.date = date;
        this.objId = objId;
        this.objName = objName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    @Override
    public String toString() {
        return "VoteToJSONView{" +
                "id=" + getId() +
                ", date=" + date +
                ", objId=" + objId +
                ", objName='" + objName + '\'' +
                '}';
    }
}
