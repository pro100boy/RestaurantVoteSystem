package ua.restaurant.vote.to;

import java.time.LocalDate;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
public class VoteTo {
    private Integer voteId;
    private LocalDate date;

    private String name;

    public VoteTo() {
    }

    public VoteTo(Integer voteId, LocalDate date, String name) {
        this.voteId = voteId;
        this.date = date;
        this.name = name;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
