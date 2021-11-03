package br.com.hyteck.investiment.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportedXSLX extends AbstractEntity {

    private LocalDate date;
    private String typeOperation;
    private String financial;
    private String code;
    private double quantity;
    private double value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportedXSLX that = (ImportedXSLX) o;
        return Objects.equals(date, that.date) && typeOperation.equals(that.typeOperation) && financial.equals(that.financial) && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, typeOperation, financial, code);
    }
}
