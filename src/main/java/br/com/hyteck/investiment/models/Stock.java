package br.com.hyteck.investiment.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Stock extends AbstractEntity {
    private String name;

    @Column(unique = true)
    private String code;
    @Enumerated(EnumType.STRING)
    private InvestmentType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stock stock = (Stock) o;
        return id != null && Objects.equals(id, stock.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}