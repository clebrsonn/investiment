package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.enums.InvestmentType;
import br.com.hyteck.investiment.enums.OperationType;
import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.models.*;
import br.com.hyteck.investiment.repository.ImportRepository;
import br.com.hyteck.investiment.repository.InvestmentRepository;
import br.com.hyteck.investiment.stocks.models.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvestmentService extends AbstractService<Investment, String> {

    private final ImportRepository importRepository;
    private final WalletService walletService;
    @Autowired
    private StockService stockService;

    protected InvestmentService(InvestmentRepository repository, ImportRepository importRepository, WalletService walletService) {
        super(repository);
        this.importRepository = importRepository;
        this.walletService = walletService;
    }
    @Override
    public InvestmentRepository getRepository() {
        return (InvestmentRepository) super.getRepository();
    }


    @Override
    public Investment validateSave(Investment investment) {
        var investmentSaved = getRepository().findByStockNameAndQuantityAndValueAndDateAndWalletId(investment.getStock().getName(),
                investment.getQuantity(), investment.getValue(), investment.getDate(), investment.getWallet().getId());
        if(investmentSaved.isPresent()){
            return null;
        }
        return investment;
    }

    public void convertAndSave(){
        List<ImportedXSLX> xslxs = importRepository.findAllByAlreadyConvertedIsFalse();
        List<String> stockCodes   = new ArrayList<>();
        Set<String> walletsNames = new HashSet<>();

        xslxs.forEach(importedXSLX -> {
            stockCodes.add(importedXSLX.getCode());
            walletsNames.add(importedXSLX.getFinancial());

        });

        var investments = new ArrayList<Investment>();
        var walletIndexedByName = walletService.findOrSaveAllByNames(walletsNames).stream().collect(Collectors.toMap(Wallet::getName, wallet -> wallet));
        var stockIndexedByCode = stockService.findByCodeIn(stockCodes).stream().collect(Collectors.toMap(Stock::getCode, stock-> stock));


        xslxs.forEach(xslx -> {
            investments.add(new Investment(UUID.randomUUID(),stockIndexedByCode.get(xslx.getCode()),
                new BigDecimal(Double.toString(xslx.getValue())).setScale(5, RoundingMode.FLOOR), xslx.getQuantity(),xslx.getDate(), InvestmentType.VARIABLE_RENT, walletIndexedByName.get(xslx.getFinancial()),
                OperationType.getByType(xslx.getTypeOperation())

            ));
            xslx.setAlreadyConverted(true);

        });

        saveAll(investments.stream().distinct().collect(Collectors.toList()));
        importRepository.saveAllAndFlush(xslxs);

    }

    public Map<String, BigDecimal> getAverage(){
        var investments = getRepository().findAll();
        var investmentsByname = investments.stream().collect(Collectors.groupingBy(investment -> investment.getStock().getCode()));
        var averageByName = new HashMap<String, BigDecimal>();
        for(var invests: investmentsByname.entrySet()){
            var quantity = BigDecimal.ZERO;
            var value = BigDecimal.ZERO;
            for(var investmet : invests.getValue()){
                value = value.add(investmet.getTotal());
                quantity = quantity.add(BigDecimal.valueOf(investmet.getQuantity()));

            }
            averageByName.put(invests.getKey(), value.divide(quantity, RoundingMode.HALF_UP));

        }
        return averageByName;
    }

    public Double getTotalAmount() {
        var buy = getRepository().findAll().stream().filter(investment -> investment.getOperationType().equals(OperationType.BUY)).mapToDouble(in -> in.getTotal().doubleValue()).sum();
        var sell = getRepository().findAll().stream().filter(investment -> investment.getOperationType().equals(OperationType.SELL)).mapToDouble(in -> in.getTotal().doubleValue()).sum();
       return buy - sell;
    }
}
