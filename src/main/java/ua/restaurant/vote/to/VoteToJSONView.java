package ua.restaurant.vote.to;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by Galushkin Pavel on 14.03.2017.
 */
public class VoteToJSONView extends VoteTo {
    @NotBlank // (проверка на символы, кроме пробелов)
    @Column(name = "name", nullable = false)
    @Size(min = 1, max = 255)
    @SafeHtml
    private String objName;

    public VoteToJSONView() {
    }

    public VoteToJSONView(Integer id, LocalDate date, Integer objId, String objName) {
        super(id, date, objId);
        this.objName = objName;
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
                ", date=" + getDate() +
                ", objId=" + getObjId() +
                ", objName='" + objName + '\'' +
                '}';
    }
}
