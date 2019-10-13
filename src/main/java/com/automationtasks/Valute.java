package com.automationtasks;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Valute")
public class Valute {
    @XStreamAlias("ID")
    @XStreamAsAttribute
    private String id;

    @XStreamAlias("NumCode")
    private String numCode;

    @XStreamAlias("CharCode")
    private String charCode;

    @XStreamAlias("Nominal")
    private int nominal;

    @XStreamAlias("Name")
    private String name;

    @XStreamAlias("Value")
    private double value;

    public Valute(){}

    public Valute(String id, String numCode, String charCode, int nominal, String name, double value) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "ID='" + id + '\'' +
                ", NumCode='" + numCode + '\'' +
                ", CharCode='" + charCode + '\'' +
                ", Nominal=" + nominal +
                ", Name='" + name + '\'' +
                ", Value=" + value +
                '}';
    }
}
