package ua.restaurant.vote;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return (getId() == null);
    }
}
