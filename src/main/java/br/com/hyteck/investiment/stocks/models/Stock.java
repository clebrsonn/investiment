package br.com.hyteck.investiment.stocks.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Stock extends AbstractEntity {
    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    private String code;

    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stock stock = (Stock) o;
        return id != null && Objects.equals(id, stock.id);
    }

    public Stock(String name, String code, String type){
        this.name = name;
        setCode(code);
        this.type = type;
    }

    public void setCode(String code) {
        code = code.split("\\.")[0].replaceFirst("F$","");
        this.code = code;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}