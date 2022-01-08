package br.com.hyteck.investiment.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OperationType {
    SELL("Venda"),
    BUY("Compra");

    private final String type;

    OperationType(String type) {
        this.type = type;
    }

    public static OperationType getByType(String type){
        return Arrays.stream(OperationType.values()).filter(operationType -> operationType.getType().equalsIgnoreCase(type)).findFirst().get();
    }
}
