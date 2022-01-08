package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.stocks.api.external.ConnectAlphaVantage;
import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.models.ImportedXSLX;
import br.com.hyteck.investiment.stocks.models.Stock;
import br.com.hyteck.investiment.repository.StockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class StockService extends AbstractService<Stock, String>{
    private final ConnectAlphaVantage alphaVantage;
    protected StockService(StockRepository repository, ConnectAlphaVantage alphaVantage) {
        super(repository);
        this.alphaVantage = alphaVantage;
    }

    @Override
    public StockRepository getRepository() {
        return (StockRepository) super.getRepository();
    }


    public void checkImportedStock(List<ImportedXSLX> xslxes) {
        var codes = xslxes.stream().map(ImportedXSLX::getCode).distinct().collect(Collectors.toMap(code-> code, code-> getRepository().existsByCode(code)));
        var codesForSave = codes.entrySet().stream().filter(stringBooleanEntry -> !stringBooleanEntry.getValue()).map(Map.Entry::getKey).collect(Collectors.toList());
        var stocks = new ArrayList<Stock>();
        AtomicInteger wait = new AtomicInteger(0);
        codesForSave.forEach(code -> {
            stocks.addAll(getStockInfoForExternal(code));

            wait.addAndGet(1);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        saveAll(stocks);

    }

    public List<Stock> getStockInfoForExternal(String code){
        Map<String, List<Map<String,String>>> stocks = null;
        try {
            stocks = (Map<String, List<Map<String,String>>>) new ObjectMapper().readValue(alphaVantage.getStock(code), Object.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Stock> st = new ArrayList<>();
        for (String key: Objects.requireNonNull(stocks).keySet()) {
            if(stocks.get(key).isEmpty()){
                st.add(new Stock(code, code, "Missing Information"));
            }
            for (Map<String, String> values : stocks.get(key)) {
                st.add(new Stock(values.get("2. name"), values.get("1. symbol"), values.get("3. type")));
            }
        }
        return st;
    }


    public Collection<Stock> findByCodeIn(Collection<String> codes) {
        return getRepository().findByCodeIn(codes);
    }

    @Override
    public Stock validateSave(Stock stock) {
        return stock;
    }

}
