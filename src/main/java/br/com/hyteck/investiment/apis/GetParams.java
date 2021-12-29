package br.com.hyteck.investiment.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:values.properties")
public class GetParams {

    @Value("${key.alpha.vantage}")
    public String alphaVantageKey;

    @Value("${key.bscscan}")
    public String bscScanKey;

}
