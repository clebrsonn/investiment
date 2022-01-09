package br.com.hyteck.investiment.resources;

import br.com.hyteck.investiment.framework.AbstractController;
import br.com.hyteck.investiment.stocks.models.StockSplit;
import br.com.hyteck.investiment.services.StockSplitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/split")
public class StockSplitController extends AbstractController<StockSplit, String> {


    public StockSplitController(StockSplitService service) {
        super(service);
    }

}
