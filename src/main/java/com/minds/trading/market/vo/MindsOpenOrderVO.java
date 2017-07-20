package com.minds.trading.market.vo;

import com.google.gson.Gson;
import java.math.BigDecimal;


public class MindsOpenOrderVO
{
    public final String orderNumber;
    public final String type;
    public final BigDecimal rate;
    public final BigDecimal amount;
    public final BigDecimal total;

    public MindsOpenOrderVO(String orderNumber, String type, BigDecimal rate, BigDecimal amount, BigDecimal total)
    {
        this.orderNumber = orderNumber;
        this.type = type;
        this.rate = rate;
        this.amount = amount;
        this.total = total;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
