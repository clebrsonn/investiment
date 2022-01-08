package br.com.hyteck.investiment.currency.dto;

import br.com.hyteck.investiment.currency.models.Currency;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ValCurs")
public class CurrencyDTO {
    private List<Valute> currencies;

    public void setCurrencies(List<Valute> currencies) {
        this.currencies = currencies;
    }
    @XmlElement(name = "Valute")
    public List<Valute> getCurrencies() {
        return currencies;
    }

    public static Collection<Currency> toCurrencies(List<Valute> valutes){
        List<Currency> currencies = new ArrayList<>();
        return valutes.stream().map(currencyDTO -> new Currency(currencyDTO.getCharCode(), currencyDTO.getName(), false)).collect(Collectors.toList());

    }
}
