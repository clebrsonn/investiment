package br.com.hyteck.investiment.resources;

import br.com.hyteck.investiment.framework.AbstractController;
import br.com.hyteck.investiment.models.Investment;
import br.com.hyteck.investiment.models.StockSplit;
import br.com.hyteck.investiment.services.InvestmentService;
import br.com.hyteck.investiment.services.StockSplitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/split")
public class StockSplitController extends AbstractController<StockSplit, UUID, StockSplitService> {


    public StockSplitController(StockSplitService service) {
        super(service);
    }

}
