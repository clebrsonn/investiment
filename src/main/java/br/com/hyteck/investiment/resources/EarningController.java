package br.com.hyteck.investiment.resources;

import br.com.hyteck.investiment.stocks.api.external.ConnectAlphaVantage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/earning/")
public class EarningController{

    private final ConnectAlphaVantage alphaVantage;

    public EarningController(ConnectAlphaVantage alphaVantage) {
        this.alphaVantage = alphaVantage;
    }

    @GetMapping("/")
    public ResponseEntity<String> getEarning(){
        String erarning =alphaVantage.getEarningCalendar();
        return ResponseEntity.ok(erarning);
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getEarningBySymbol(@PathVariable String name){
        String erarning =alphaVantage.getEarningCalendarBySymbol(name);
        return ResponseEntity.ok(erarning);
    }

}
