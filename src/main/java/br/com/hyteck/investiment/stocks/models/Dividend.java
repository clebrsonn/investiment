package br.com.hyteck.investiment.stocks.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Dividend extends AbstractEntity {
    private String type;
    private LocalDate date;
    @NotNull
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name="stock", nullable = false)
    @NotNull
    private Stock stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dividend dividend = (Dividend) o;
        return id != null && Objects.equals(id, dividend.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
