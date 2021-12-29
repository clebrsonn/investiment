package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.apis.external.ConnectAlphaVantage;
import br.com.hyteck.investiment.apis.external.alphavantage.StockInfo;
import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.models.InvestmentType;
import br.com.hyteck.investiment.models.Stock;
import br.com.hyteck.investiment.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StockService extends AbstractService<Stock, String>{
    @Autowired
    private ConnectAlphaVantage alphaVantage;
    protected StockService(StockRepository repository) {
        super(repository);
    }

    @Override
    public StockRepository getRepository() {
        return (StockRepository) super.getRepository();
    }

    @Override
    public Stock validateSave(Stock stock) {
        if(getRepository().existsByCode(stock.getCode())){
            return null;
        }else{
            StockInfo stocks=  alphaVantage.getStock(stock.getCode());
            var st = stocks.getBestMatches().stream().map(received -> new Stock(received.get("2. name"), received.get("1. symbol"), InvestmentType.VARIABLE_RENT)).collect(Collectors.toList());
            if(st.size()>1) {
                st = saveAll(st);
            }
            return st.get(0);
        }
    }
}
