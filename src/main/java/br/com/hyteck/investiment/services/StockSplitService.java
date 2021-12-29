package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.models.StockSplit;
import br.com.hyteck.investiment.repository.InvestmentRepository;
import br.com.hyteck.investiment.repository.StockSplitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Service
public class StockSplitService extends AbstractService<StockSplit, String> {

    private final InvestmentRepository investmentRepository;

    protected StockSplitService(StockSplitRepository repository, InvestmentRepository investmentRepository) {
        super(repository);
        this.investmentRepository = investmentRepository;
    }

    @Override
    public StockSplit validateSave(StockSplit stockSplit) {
        var investments= investmentRepository.findAllByStockNameAndDateLessThanEqual(stockSplit.getName(), stockSplit.getDate());
        var investmentsToSave = investments.parallelStream().peek(investment -> {
           var value= BigDecimal.valueOf(investment.getQuantity()).multiply(stockSplit.getTo().divide(stockSplit.getFrom(), 5, RoundingMode.HALF_DOWN));
           investment.setQuantity(value.doubleValue());

           investment.setValueBuy(investment.getValueBuy().divide(stockSplit.getTo().divide(stockSplit.getFrom(),4, RoundingMode.HALF_UP),4, RoundingMode.HALF_UP));
        }).collect(Collectors.toList());
        investmentRepository.saveAll(investmentsToSave);
        stockSplit.setStock(investmentsToSave.get(0).getStock());
        return stockSplit;
    }
}
