package br.com.hyteck.investiment.resources;

import br.com.hyteck.investiment.framework.AbstractController;
import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.services.DividendService;
import br.com.hyteck.investiment.stocks.models.Dividend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dividends")
public class DividendController extends AbstractController<Dividend, String> {

    public DividendController(DividendService service) {
        super(service);
    }
    @GetMapping("/get-by-integration")
    public void getDividends(){
        getService().findDividends();
    }

    @Override
    protected DividendService getService() {
        return (DividendService) super.getService();
    }
}
