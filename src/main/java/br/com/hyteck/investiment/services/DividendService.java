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
    private InvestmentService investmentService;

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
        var investments = investmentService.findAll();
        List<String> divdends = new ArrayList<>();

        investments.forEach(investment -> {
            connectAlphaVantage.getEarningCalendarBySymbol(investment.getStock().getCode());
        });

    }
}
