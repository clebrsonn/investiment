package br.com.hyteck.investiment.models;

import br.com.hyteck.investiment.enums.InvestmentType;
import br.com.hyteck.investiment.enums.OperationType;
import br.com.hyteck.investiment.framework.AbstractEntity;
import br.com.hyteck.investiment.stocks.models.Stock;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "date", "quantity", "value", "stock", "wallet" }) })
public class Investment extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name="stock")
    private Stock stock;
    private BigDecimal value;
    private Double quantity;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 20, fraction = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "wallet")
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    public Investment(UUID id, Stock stock, BigDecimal valueBuy, Double quantity, LocalDate date, InvestmentType type, Wallet wallet, OperationType operationType) {
        super(id.toString());
        this.stock=stock;
        this.value = valueBuy;
        this.quantity = quantity;
        this.date = date;
        this.wallet = wallet;
        this.operationType = operationType;
        //calculateTotal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investment that = (Investment) o;
        return stock.equals(that.stock) && value.equals(that.value) && quantity.equals(that.quantity) && date.equals(that.date) && wallet.equals(that.wallet);
    }
    @PrePersist
    @PreUpdate
    private void calculateTotal() {
        this.total = BigDecimal.valueOf(getQuantity()).multiply(getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock, value, quantity, date, wallet, operationType);
    }
}
