package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.currency.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
    Integer countByCode(String code);
}
