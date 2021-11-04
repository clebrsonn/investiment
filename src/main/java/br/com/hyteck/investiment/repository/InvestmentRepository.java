package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<Investment, UUID> {
    Optional<Investment> findByNameAndQuantityAndValueBuyAndDateAndWalletId(String name, Double quantity
            , BigDecimal valueBuy, LocalDate date, UUID walletId);
    Collection<Investment> findAllByNameAndDateLessThanEqual(String name, LocalDate date);
}