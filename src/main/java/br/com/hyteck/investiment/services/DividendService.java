package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.repository.DividendRepository;
import br.com.hyteck.investiment.stocks.api.external.ConnectAlphaVantage;
import br.com.hyteck.investiment.stocks.models.Dividend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DividendService extends AbstractService<Dividend, String> {
    @Autowired
    private ConnectAlphaVantage connectAlphaVantage;

    @Autowired
    private StockService stockService;

    public DividendService(JpaRepository<Dividend, String> repository) {
        super(repository);
    }

    @Override
    public DividendRepository getRepository() {
        return (DividendRepository) super.getRepository();
    }

    @Override
    public Dividend validateSave(Dividend dividend) {
        return dividend;
    }

    public void findDividends(){
        var investments = stockService.findAll();
        List<String> dividends = new ArrayList<>();

        investments.forEach(investment -> {
           dividends.add(connectAlphaVantage.getEarningCalendarBySymbol(investment.getCodeIntegration()));
        });
        dividends.forEach(System.out::println);
    }
}
