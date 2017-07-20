package com.minds.trading.market.vo;

import com.google.gson.Gson;
import java.util.List;


public class MindsOrderResultVO
{
    public final Long orderNumber;
    public final String error;
    public final List<MindsTradeHistoryVO> resultingTrades;

    public MindsOrderResultVO(Long orderNumber, List<MindsTradeHistoryVO> resultingTrades, String error)
    {
        this.orderNumber = orderNumber;
        this.resultingTrades = resultingTrades;
        this.error = error;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
