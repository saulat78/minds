package com.minds.trading.market.vo;

import com.google.gson.Gson;

public class MindsChartDataVO
{
    public final String date;
    public final String high;
    public final String low;
    public final String open;
    public final String close;
    public final String volume;
    public final String quoteVolume;
    public final String weightedAverage;

    public MindsChartDataVO(String date, String high, String low, String open, String close, String volume, String quoteVolume, String weightedAverage)
    {
        this.date = date;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.quoteVolume = quoteVolume;
        this.weightedAverage = weightedAverage;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
