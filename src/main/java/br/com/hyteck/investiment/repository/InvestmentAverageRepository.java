package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.trade.model.InvestmentAverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentAverageRepository extends JpaRepository<InvestmentAverage, String> {
}
