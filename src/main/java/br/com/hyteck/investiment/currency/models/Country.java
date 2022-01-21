package br.com.hyteck.investiment.currency.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import br.com.hyteck.investiment.users.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Country extends AbstractEntity {
    @ManyToMany
    @JoinTable(name="country_currency", joinColumns=
            {@JoinColumn(name="country_id")}, inverseJoinColumns=
            {@JoinColumn(name="currency_id")})
    @ToString.Exclude
    private List<Currency> currencies;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Country country = (Country) o;
        return id != null && Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
