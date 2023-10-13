package com.fundguide.melona.chart1.dto;

import lombok.Data;

@Data
public class ChatDto {
    private String symbol;
    private String name;
    private double price;
    private double changesPercentage;
    private double change;
    private double dayLow;
    private double dayHigh;
    private double yearHigh;
    private double yearLow;
    private long marketCap;
    private double priceAvg50;
    private double priceAvg200;
    private String exchange;
    private int volume;
    private int avgVolume;
    private double open;
    private double previousClose;
    private double eps;
    private double pe;
    private String earningsAnnouncement;
    private long sharesOutstanding;
    private long timestamp;
}
