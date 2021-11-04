package br.com.hyteck.investiment.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StockSplit extends AbstractEntity {

    private LocalDate date;
    @Column(name = "oldQuantity")
    private BigDecimal to;
    @Column(name ="newQuantity")
    private BigDecimal from;
    private String name;

    @ManyToMany
    @JoinTable(name = "stock_split_investments",
            joinColumns = @JoinColumn(name = "stock_split_id"),
            inverseJoinColumns = @JoinColumn(name = "investments_id"))
    private List<Investment> investments;

    public StockSplit(UUID id, List<Investment> investments, LocalDate date, BigDecimal to, BigDecimal from) {
        super(id);
        this.investments = investments;
        this.date = date;
        this.to = to;
        this.from = from;
    }
}
