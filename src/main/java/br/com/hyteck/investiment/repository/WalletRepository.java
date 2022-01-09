package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.stocks.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface WalletRepository extends JpaRepository<Wallet, String> {
    Collection<Wallet> findDistinctByNameIn(Collection<String> names);
}
