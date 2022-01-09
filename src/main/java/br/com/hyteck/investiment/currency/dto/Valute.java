package br.com.hyteck.investiment.currency.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Valute{
    private Integer numCode;
    private String charCode;
    private Integer nominal;
    private String name;
    private String value;

    @XmlElement(name = "NumCode")
    public Integer getNumCode() {
        return numCode;
    }

    @XmlElement(name = "CharCode")
    public String getCharCode() {
        return charCode;
    }

    @XmlElement(name = "Nominal")
    public Integer getNominal() {
        return nominal;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "Value")
    public String getValue() {
        return value;
    }

}

