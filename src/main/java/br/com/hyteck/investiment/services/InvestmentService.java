package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.models.ImportedXSLX;
import br.com.hyteck.investiment.models.Investment;
import br.com.hyteck.investiment.models.InvestmentType;
import br.com.hyteck.investiment.models.Wallet;
import br.com.hyteck.investiment.repository.ImportRepository;
import br.com.hyteck.investiment.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvestmentService extends AbstractService<Investment, UUID, InvestmentRepository> {

    private final ImportRepository importRepository;
    private final WalletService walletService;

    protected InvestmentService(InvestmentRepository repository, ImportRepository importRepository, WalletService walletService) {
        super(repository);
        this.importRepository = importRepository;
        this.walletService = walletService;
    }

    @Override
    public Investment validateSave(Investment investment) {
        var investmentSaved = getRepository().findByNameAndQuantityAndValueBuyAndDateAndWalletId(investment.getName(),
                investment.getQuantity(), investment.getValueBuy(), investment.getDate(), investment.getWallet().getId());
        if(investmentSaved.isPresent()){
            return null;
        }
        return investment;
    }

    public List<Investment> from(){
        List<ImportedXSLX> xslxs = importRepository.findAll();
        var financialNames = xslxs.stream().map(ImportedXSLX::getFinancial).collect(Collectors.toSet());
        var walletIndexedByName = walletService.findOrSaveAllByNames(financialNames).stream().collect(Collectors.toMap(Wallet::getName, wallet -> wallet));
        var investments = new ArrayList<Investment>();
        xslxs.forEach(xslx -> investments.add(new Investment(UUID.randomUUID(),xslx.getCode(), new BigDecimal(Double.toString(xslx.getValue())).setScale(5, RoundingMode.FLOOR), xslx.getQuantity(),xslx.getDate(), InvestmentType.VARIABLE_RENT, walletIndexedByName.get(xslx.getFinancial()))));
        return investments;
    }

    public Map<String, BigDecimal> getAverage(){
        var investments = getRepository().findAll();
        var investmentsByname = investments.stream().collect(Collectors.groupingBy(Investment::getName));
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

}
