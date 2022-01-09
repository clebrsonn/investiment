package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.trade.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, String> {
    Optional<Investment> findByStockNameAndQuantityAndValueAndDateAndWalletId(String name, Double quantity
            , BigDecimal value, LocalDate date, String walletId);


    Collection<Investment> findAllByStockCodeAndDateLessThanEqual(String name, LocalDate date);
}