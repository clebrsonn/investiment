package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.models.StockSplit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockSplitRepository extends JpaRepository<StockSplit, UUID> {
}