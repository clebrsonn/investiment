package br.com.hyteck.investiment.stocks.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import br.com.hyteck.investiment.users.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
public class Wallet extends AbstractEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Wallet(UUID uuid, String name) {
        super.id= uuid.toString();
        this.name=name;
    }

    public Wallet() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Wallet wallet = (Wallet) o;
        return id != null && Objects.equals(id, wallet.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
