package br.com.hyteck.investiment.repository;

import br.com.hyteck.investiment.stocks.models.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository  extends JpaRepository<Dividend, String> {
}
