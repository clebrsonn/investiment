package br.com.hyteck.investiment.resources;

import br.com.hyteck.investiment.framework.AbstractController;
import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.trade.model.Investment;
import br.com.hyteck.investiment.services.InvestmentService;
import br.com.hyteck.investiment.stocks.api.external.B3Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/investment/")
public class InvestmentController extends AbstractController<Investment, String> {

    @Autowired
    private B3Consumer b3Consumer;

    public InvestmentController(InvestmentService service) {
        super(service);
    }

    @GetMapping("/average")
    public ResponseEntity<Map<String, BigDecimal>> getAverageValue(){
        return ResponseEntity.ok(getService().getAverage());
    }

    @GetMapping("/amount")
    public ResponseEntity<Double> getAmountInvested(){
        return ResponseEntity.ok(getService().getTotalAmount());
    }

    @PostMapping("/b3")
    public void getB3(){
        b3Consumer.getPositions("06824530484", LocalDate.now());
    }

    @Override
    protected InvestmentService getService() {
        return (InvestmentService) super.getService();
    }
}
