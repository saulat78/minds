package com.minds.trading.exchange;

import java.math.BigDecimal;


public interface TradingAPIClient
{
    public String returnBalances();

    public String returnCompleteBalances();

    public String returnFeeInfo();

    public String returnOpenOrders(String currencyPair);

    public String returnTradeHistory(String currencyPair);

    public String cancelOrder(String orderNumber);

    public String moveOrder(String orderNumber, BigDecimal rate);

    public String sell(String currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

    public String buy(String currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

   

}
