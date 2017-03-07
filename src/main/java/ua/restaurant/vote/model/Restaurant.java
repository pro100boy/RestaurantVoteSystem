package ua.restaurant.vote.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@NamedEntityGraph(name = Restaurant.GRAPH_WITH_VOTES, attributeNodes = {@NamedAttributeNode("votes")})
@Table(name = "restaurants")
public class Restaurant extends NamedEntity {
    public static final String GRAPH_WITH_VOTES = "Restaurant.withVotes";

    @NotBlank
    @Column(name = "description", nullable = false)
    @Size(min = 5, max = 255)
    @SafeHtml
    private String description;

    @SuppressWarnings("JpaQlInspection")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date_time DESC")
    protected List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getDescription());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + getId() +
                ", description='" + description + '\'' +
                //", votes=" + votes +
                '}';
    }
}
