package br.com.hyteck.investiment.events;

import br.com.hyteck.investiment.models.ImportedXSLX;
import br.com.hyteck.investiment.repository.ImportRepository;
import br.com.hyteck.investiment.services.InvestmentService;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class UploadEventListener {
    private final ImportRepository importRepository;
    @Autowired
    private InvestmentService investmentService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UploadEventListener(ImportRepository importRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.importRepository = importRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    @EventListener({UploadEvent.class})
    public CompletableFuture<Void> onApplicationEvent(UploadEvent event) {
        ExecutorService pool = Executors.newCachedThreadPool();
        return CompletableFuture.runAsync(() -> importXLS(event.getPayload()), pool);

    }

    @Async
    @EventListener({UploadEvent.ImportFinish.class})
    public CompletableFuture<Void> onApplicationEvent(UploadEvent.ImportFinish event) {
        ExecutorService pool = Executors.newCachedThreadPool();
        return CompletableFuture.runAsync(()->{
                var investiments = investmentService.from();
                investmentService.saveAll(investiments);
            }, pool);
    }

    @SneakyThrows
    public void importXLS(String inp){
        try{
            XSSFWorkbook wb = new XSSFWorkbook(inp);
            XSSFSheet sheet = wb.getSheetAt(0);
            List<ImportedXSLX> xslxes = new ArrayList<>();
            sheet.removeRow(sheet.getRow(0));
            sheet.rowIterator().forEachRemaining(row ->{
                ImportedXSLX xslx = new ImportedXSLX(LocalDate.parse(row.getCell(0).getStringCellValue(), DateTimeFormatter.ofPattern("d/MM/yyyy"))
                        , row.getCell(1).getStringCellValue()
                        , row.getCell(4).getStringCellValue()
                        , row.getCell(5).getStringCellValue()
                        , row.getCell(6).getNumericCellValue()
                        , row.getCell(7).getNumericCellValue());
                xslx.setId(UUID.randomUUID().toString());
                xslxes.add(xslx);

            });
            importRepository.saveAll(xslxes);
        } catch (IOException e) {
            throw new RuntimeException("error", e.getCause());
        }finally {
            Files.deleteIfExists(Path.of(inp));
            applicationEventPublisher.publishEvent(new UploadEvent.ImportFinish(this));

        }
    }
}
