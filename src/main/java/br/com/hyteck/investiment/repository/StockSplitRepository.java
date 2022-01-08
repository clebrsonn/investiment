package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.stocks.models.StockSplit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockSplitRepository extends JpaRepository<StockSplit, String> {
}