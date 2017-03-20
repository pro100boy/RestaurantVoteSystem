package ua.restaurant.vote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
@SuppressWarnings("JpaQlInspection")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@NamedEntityGraph(name = Restaurant.GRAPH_WITH_VOTES_MENUS, attributeNodes =
        {
                @NamedAttributeNode("votes"),
                @NamedAttributeNode("menus")
        })
@Table(name = "restaurants")
public class Restaurant extends NamedEntity {
    public static final String GRAPH_WITH_VOTES_MENUS = "Restaurant.withVotesMenus";

    @NotBlank
    @Column(name = "description", nullable = false)
    @Size(min = 5, max = 255)
    @SafeHtml
    private String description;

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("vote_date DESC")
    @JsonManagedReference(value="restaurant-votes")
    private Set<Vote> votes;

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("menu_date DESC")
    @JsonManagedReference(value="restaurant-menus")
    private Set<Menu> menus;

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

    public Set<Vote> getVotes() {
        return votes;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + getId() +
                ", description='" + description + '\'' +
                //", votes=" + votes +
                //", menus=" + menus +
                '}';
    }
}
