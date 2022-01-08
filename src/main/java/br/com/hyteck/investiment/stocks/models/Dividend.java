package br.com.hyteck.investiment.stocks.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import br.com.hyteck.investiment.stocks.models.Stock;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Dividend extends AbstractEntity {
    private String type;
    private LocalDate date;
    @NotNull
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name="stock", nullable = false)
    @NotNull
    private Stock stock;
}
