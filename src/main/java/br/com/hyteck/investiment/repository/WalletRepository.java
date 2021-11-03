package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Collection<Wallet> findDistinctByNameIn(Collection<String> names);
}
