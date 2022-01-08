package br.com.hyteck.investiment.currency.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Currency extends AbstractEntity {
    @NotBlank
    @Size(max = 3, min = 3)
    private String code;
    @NotBlank
    private String name;
    private Boolean defaultCurrency;


}
