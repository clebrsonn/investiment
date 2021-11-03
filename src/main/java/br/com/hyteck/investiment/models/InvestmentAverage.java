package br.com.hyteck.investiment.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class InvestmentAverage {
    @Id
    private String id;
    private BigDecimal averageAmount;


}
