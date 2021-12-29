package br.com.hyteck.investiment.apis.external;

import br.com.hyteck.investiment.apis.GetParams;
import br.com.hyteck.investiment.apis.external.alphavantage.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "alphaVantage", url = "${url.alpha.vantage}")
public interface ConnectAlphaVantage {
    String api= "Q4XKNXZJ5ZNSIY1Q";
    @GetMapping(value = "/query?function=EARNINGS_CALENDAR&" +
            "horizon=3month&apikey=" +api)
    String getEarningCalendar();

    @GetMapping(value = "/query?function=EARNINGS_CALENDAR&" +
            "horizon=3month&symbol={symbol}&apikey="+ api)
    String getEarningCalendarBySymbol(String symbol);

    @GetMapping(value="/query?function=SYMBOL_SEARCH&keywords={symbol}&apikey=Q4XKNXZJ5ZNSIY1Q")
    StockInfo getStock(String symbol);

}
