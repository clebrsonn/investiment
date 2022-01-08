package br.com.hyteck.investiment.events;

import org.springframework.context.ApplicationEvent;

public class ConvertedObjectsEvent extends ApplicationEvent {
    private static final long serialVersionUID = -1882688834564662543L;


    public ConvertedObjectsEvent(Object source) {
        super(source);
    }
}
