package ua.restaurant.vote.to;

/**
 * Created by Galushkin Pavel on 10.03.2017.
 */

public class VoteTo {
    //r.ID, r.NAME, COUNT(v.REST_ID)
    private Integer id;
    private String name;
    private Integer cnt;

    public VoteTo() {
    }

    public VoteTo(Integer id, String name, Integer cnt) {
        this.id = id;
        this.name = name;
        this.cnt = cnt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCnt() {
        return cnt;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "restaurantId=" + id +
                ", name='" + name + '\'' +
                ", votes=" + cnt +
                '}';
    }
}
