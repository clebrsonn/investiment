package br.com.hyteck.investiment.apis.external;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "bscscan", url = "${url.bscscan}")
public interface ConnectBscScan {
    String api="13XV3BZHR2Z9TI9E2G3JIZTX49ZFADTKVC";
    @GetMapping(value = "/api?module=account&" +
            "action=balance&" +
            "address={address}" +
            "&apikey=" + api)
    String getAccount(String address);

    @GetMapping(value = "/api?module=transaction&" +
            "action=gettxreceiptstatus&"+
            "txhash={txHash}&apikey=" + api)
    String getTransaction(String txHash);

    @GetMapping(value="/api?module=gastracker&action=gasoracle&apikey="+api)
    String getGasOracle();

}
