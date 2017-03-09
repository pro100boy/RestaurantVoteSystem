package ua.restaurant.vote.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * User: Galushkin Pavel
 * Date: 15.02.2017
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotBlank // (проверка на символы, кроме пробелов)
    @Column(name = "name", nullable = false)
    @Size(min = 1, max = 255)
    @SafeHtml
    protected String name;

    public NamedEntity() {
    }

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}

