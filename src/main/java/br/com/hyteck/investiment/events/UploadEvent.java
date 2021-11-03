package br.com.hyteck.investiment.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

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

        public ImportFinish(Object source) {
            super(source);

        }
    }
}
