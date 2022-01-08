package br.com.hyteck.investiment.events;

import br.com.hyteck.investiment.models.ImportedXSLX;
import br.com.hyteck.investiment.services.ImportXSLXService;
import br.com.hyteck.investiment.services.InvestmentService;
import br.com.hyteck.investiment.services.StockService;
import br.com.hyteck.investiment.services.WalletService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class MyEventListener {
    private final ImportXSLXService importXSLXService;
    private final InvestmentService investmentService;
    private final WalletService walletService;
    private final StockService stockService;


    public MyEventListener(ImportXSLXService importXSLXService, InvestmentService investmentService, WalletService walletService, StockService stockService) {
        this.importXSLXService = importXSLXService;
        this.investmentService = investmentService;
        this.walletService = walletService;
        this.stockService = stockService;
    }

    @Async
    @EventListener({UploadEvent.class})
    public CompletableFuture<Void> onApplicationEvent(UploadEvent event) {
        ExecutorService pool = Executors.newCachedThreadPool();
        return CompletableFuture.runAsync(() -> importXSLXService.importXLSToDatabase(event.getPayload()), pool);

    }

    @Async
    @EventListener({ConvertedObjectsEvent.class})
    public CompletableFuture<Void> onApplicationEvent() {
        ExecutorService pool = Executors.newCachedThreadPool();
        return CompletableFuture.runAsync(investmentService::convertAndSave, pool);
    }

    @Async
    @EventListener({UploadEvent.ImportFinish.class})
    public CompletableFuture<Void> onApplicationEvent(UploadEvent.ImportFinish event) {
        ExecutorService pool = Executors.newCachedThreadPool();
        return CompletableFuture.runAsync(()->{
            stockService.checkImportedStock(event.getXslxes());
            var financialNames = event.getXslxes().stream().map(ImportedXSLX::getFinancial).collect(Collectors.toSet());
            walletService.findOrSaveAllByNamesEvent(financialNames);

        }, pool);
    }


}
