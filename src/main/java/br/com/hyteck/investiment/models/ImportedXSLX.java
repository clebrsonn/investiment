package br.com.hyteck.investiment.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportedXSLX extends AbstractEntity {
    @Past
    private LocalDate date;
    private String typeOperation;
    private String financial;
    @NotBlank
    private String code;
    @NotBlank
    private double quantity;
    @NotBlank
    private double value;
    private Boolean alreadyConverted = false;

    public ImportedXSLX(LocalDate date, String typeOperation, String financial, String code, double quantity, double value) {
        this.date = date;
        this.typeOperation = typeOperation;
        this.financial = financial;
        this.code = code;
        this.quantity = quantity;
        this.value = value;
    }

    public String getCode() {
        return code.split("\\.")[0].replaceFirst("F$","");
    }

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
