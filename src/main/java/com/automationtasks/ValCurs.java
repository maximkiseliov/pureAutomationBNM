package com.automationtasks;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Date;
import java.util.List;

@XStreamAlias("ValCurs")
public class ValCurs {
    @XStreamAlias("Date")
    @XStreamAsAttribute
    private String date;

    @XStreamAsAttribute
    private String name;

    @XStreamImplicit(itemFieldName = "Valute")
    private List<Valute> valuteList;

    public ValCurs(){}

    public ValCurs(String date, String name, List<Valute> valuteList) {
        this.date = date;
        this.name = name;
        this.valuteList = valuteList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Valute> getValuteList() {
        return valuteList;
    }

    public void setValuteList(List<Valute> valuteList) {
        this.valuteList = valuteList;
    }

    @Override
    public String toString() {
        return "ValCurs{" +
                "Date=" + date +
                ", name='" + name + '\'' +
                ", valuteList=" + valuteList +
                '}';
    }
}
