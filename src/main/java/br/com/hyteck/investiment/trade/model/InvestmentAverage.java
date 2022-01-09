package br.com.hyteck.investiment.trade.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class InvestmentAverage {
    @Id
    private String id;
    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 20, fraction = 2)
    private BigDecimal averageAmount;


}
