package br.com.hyteck.investiment.config;

import br.com.hyteck.investiment.currency.api.external.CurrencyConsumer;
import br.com.hyteck.investiment.currency.dto.CurrencyDTO;
import br.com.hyteck.investiment.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Objects;

@Component
public class AfterLoadingConfiguration {
    @Autowired
    private CurrencyService currencyService;

    @EventListener({ApplicationReadyEvent.class, ContextRefreshedEvent.class})
    @Transactional
    public void loadAllcurrencies(){
        currencyService.getCurrenciesFromExternal();
    }

    @PostConstruct
    public void initSsl(){
        //System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.keyStore", getClass().getClassLoader().getResource("keystore/41259290000170.cer").getPath());
        System.setProperty("javax.net.ssl.keyStorePassword", "HHFSSZ");
        System.setProperty("javax.net.ssl.trustStorePassword", getClass().getClassLoader().getResource("keystore/41259290000170.key").getPath());
        System.setProperty("javax.net.ssl.trustStore", getClass().getClassLoader().getResource("keystore/41259290000170.p12").getPath());
    }
}
