package br.com.hyteck.investiment.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "date", "quantity", "valueBuy", "stock", "wallet" }) })
public class Investment extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name="stock")
    private Stock stock;
    private BigDecimal valueBuy;
    private Double quantity;
    private LocalDate date;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "wallet")
    private Wallet wallet;

    public Investment(UUID id, Stock stock, BigDecimal valueBuy, Double quantity, LocalDate date, InvestmentType type, Wallet wallet) {
        super(id.toString());
        this.stock=stock;
        this.valueBuy = valueBuy;
        this.quantity = quantity;
        this.date = date;
        this.wallet = wallet;
        //calculateTotal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investment that = (Investment) o;
        return stock.equals(that.stock) && valueBuy.equals(that.valueBuy) && quantity.equals(that.quantity) && date.equals(that.date) && wallet.equals(that.wallet);
    }
    @PrePersist
    @PreUpdate
    private void calculateTotal() {
        this.total = BigDecimal.valueOf(getQuantity()).multiply(getValueBuy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock, valueBuy, quantity, date, wallet);
    }
}
