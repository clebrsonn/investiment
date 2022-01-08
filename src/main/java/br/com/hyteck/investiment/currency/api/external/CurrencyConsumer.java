package br.com.hyteck.investiment.currency.api.external;

import br.com.hyteck.investiment.currency.dto.CurrencyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class CurrencyConsumer {
    @Value(value ="${url.currency}")
    private String url;

    public ResponseEntity<CurrencyDTO> getCurrencies(){
        url = url.concat("/XML_daily_eng.asp");
        var restTemplate = new RestTemplate();
        // JAXBContext context = JAXBContext.newInstance(CurrencyDTO.class);
        // Unmarshaller un = context.createUnmarshaller();
        //var currencyDTO = (CurrencyDTO) un.unmarshal(new StringReader(currencies));

        return restTemplate.getForEntity(url, CurrencyDTO.class);
    }
}
