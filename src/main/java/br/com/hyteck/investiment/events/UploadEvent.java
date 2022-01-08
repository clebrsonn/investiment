package br.com.hyteck.investiment.events;

import br.com.hyteck.investiment.models.ImportedXSLX;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class UploadEvent extends ApplicationEvent {
    private static final long serialVersionUID = -2680668406815551230L;
    private final String payload;

    public UploadEvent(Object source, String payload) {
        super(source);
        this.payload = payload;
    }
    @Getter
    public static class ImportFinish extends ApplicationEvent{
        private final List<ImportedXSLX> xslxes;

        public ImportFinish(Object source, List<ImportedXSLX> xslxes) {
            super(source);
            this.xslxes = xslxes;
        }
    }
}
