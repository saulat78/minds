package com.minds.trading.market.vo;

import com.google.gson.Gson;
import java.math.BigDecimal;


public class MindsCompleteBalanceVO
{
    public final BigDecimal available;
    public final BigDecimal onOrders;
    public final BigDecimal btcValue;

    public MindsCompleteBalanceVO(BigDecimal available, BigDecimal onOrders, BigDecimal btcValue)
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
