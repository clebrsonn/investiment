package br.com.hyteck.investiment.stocks.api.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "alphaVantage", url = "${url.alpha.vantage}")
public interface ConnectAlphaVantage {
    String api= "Q4XKNXZJ5ZNSIY1Q";
    @GetMapping(value = "/query?function=EARNINGS_CALENDAR&" +
            "horizon=3month&apikey=" +api)
    String getEarningCalendar();

    @GetMapping(value = "/query?function=EARNINGS_CALENDAR&" +
            "horizon=3month&symbol={symbol}&apikey="+ api)
    String getEarningCalendarBySymbol(@PathVariable String symbol);

    @GetMapping(value="/query?function=SYMBOL_SEARCH&keywords={symbol}&apikey=Q4XKNXZJ5ZNSIY1Q")
    String getStock(@PathVariable String symbol);

}
