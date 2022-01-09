package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.events.UploadEvent;
import br.com.hyteck.investiment.models.ImportedXSLX;
import br.com.hyteck.investiment.repository.ImportRepository;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImportXSLXService {
    @Autowired
    private ImportRepository importRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void importXLSToDatabase(String path) {
        List<ImportedXSLX> xslxes = new ArrayList<>();
        try{
            XSSFWorkbook wb = new XSSFWorkbook(path);
            XSSFSheet sheet = wb.getSheetAt(0);
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
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error", e.getCause());
        }finally {
            try {
                Files.deleteIfExists(Path.of(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            applicationEventPublisher.publishEvent(new UploadEvent.ImportFinish(this, xslxes));

        }
    }
}
