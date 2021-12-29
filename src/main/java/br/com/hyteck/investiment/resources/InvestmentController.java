package br.com.hyteck.investiment.resources;

import br.com.hyteck.investiment.framework.AbstractController;
import br.com.hyteck.investiment.models.Investment;
import br.com.hyteck.investiment.services.InvestmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/investment/")
public class InvestmentController extends AbstractController<Investment, String, InvestmentService> {


    public InvestmentController(InvestmentService service) {
        super(service);
    }

    @GetMapping("/average")
    public ResponseEntity<Map<String, BigDecimal>> getAverageValue(){
        return ResponseEntity.ok(getService().getAverage());
    }
}
