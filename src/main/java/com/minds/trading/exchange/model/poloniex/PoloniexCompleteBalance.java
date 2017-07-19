package com.minds.trading.exchange.model.poloniex;

import com.google.gson.Gson;
import java.math.BigDecimal;


public class PoloniexCompleteBalance
{
    public final BigDecimal available;
    public final BigDecimal onOrders;
    public final BigDecimal btcValue;

    public PoloniexCompleteBalance(BigDecimal available, BigDecimal onOrders, BigDecimal btcValue)
    {
        this.available = available;
        this.onOrders = onOrders;
        this.btcValue = btcValue;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
