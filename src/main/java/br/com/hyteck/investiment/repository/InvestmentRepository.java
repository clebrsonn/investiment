package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<Investment, String> {
    Optional<Investment> findByStockNameAndQuantityAndValueBuyAndDateAndWalletId(String name, Double quantity
            , BigDecimal valueBuy, LocalDate date, String walletId);


    Collection<Investment> findAllByStockNameAndDateLessThanEqual(String name, LocalDate date);
}