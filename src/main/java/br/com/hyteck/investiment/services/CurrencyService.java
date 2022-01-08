package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.currency.api.external.CurrencyConsumer;
import br.com.hyteck.investiment.currency.dto.CurrencyDTO;
import br.com.hyteck.investiment.currency.models.Currency;
import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

@Service
public class CurrencyService extends AbstractService<Currency, String> {
    @Autowired
    private CurrencyConsumer currencyConsumer;

    @Autowired
    private MessageSource messageSource;

    public CurrencyService(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    public CurrencyRepository getRepository() {
        return (CurrencyRepository) super.getRepository();
    }

    @Override
    public Currency validateSave(Currency currency) {
        if(getRepository().countByCode(currency.getCode())>=1){
            return null;
        }
        return currency;
    }

    public Collection<Currency> getCurrenciesFromExternal(){
        var response = currencyConsumer.getCurrencies();
        if(response.getStatusCode().is2xxSuccessful()){
           var currencies =  CurrencyDTO.toCurrencies(Objects.requireNonNull(response.getBody()).getCurrencies());
           return saveAll(currencies);
        }else{
            throw new RuntimeException(messageSource.getMessage("error.call.external.endpoint", null, Locale.getDefault()));
        }

    }
}
