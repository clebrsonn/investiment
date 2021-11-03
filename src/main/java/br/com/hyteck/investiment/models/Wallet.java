package br.com.hyteck.investiment.models;

import br.com.hyteck.investiment.framework.AbstractEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
public class Wallet extends AbstractEntity {
    private String name;

    public Wallet(UUID uuid, String name) {
        super.id= uuid;
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
