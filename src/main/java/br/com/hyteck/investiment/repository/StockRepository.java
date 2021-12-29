package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
     boolean existsByCode(String code);
}