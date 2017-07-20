package com.minds.trading.exchange;

import java.math.BigDecimal;
import java.util.List;

import com.minds.trading.market.vo.MindsChartDataVO;
import com.minds.trading.market.vo.MindsCoinDataVO;
import com.minds.trading.market.vo.MindsCompleteBalanceVO;
import com.minds.trading.market.vo.MindsFeeInfoVO;
import com.minds.trading.market.vo.MindsOpenOrderVO;
import com.minds.trading.market.vo.MindsOrderResultVO;
import com.minds.trading.market.vo.MindsTradeHistoryVO;



public interface MindsExchangeService 
{
    public final static String USDT_BTC_CURRENCY_PAIR = "USDT_BTC";
    public final static String ALL = "all";
    
    public final static String DGB_BTC_CURRENCY_PAIR = "BTC_DGB";
    public final static String USDT_ETH_CURRENCY_PAIR = "USDT_ETH";
    public final static String BTC_CURRENCY_TYPE = "BTC";
    public final static String ETH_CURRENCY_TYPE = "ETH";
    public final static Long FIVE_MINUTES_TIME_PERIOD = 300L;
    public final static Long FIFTEEN_MINUTES_TIME_PERIOD = 900L;
    public final static Long FOUR_HOUR_TIME_PERIOD = 14_400L;
    public final static Long TWO_HOUR_TIME_PERIOD = 7_200L;
    public final static Long DAILY_TIME_PERIOD = 86_400L;
    public final static Long LONG_LONG_AGO = 1_439_000_000L;
    
    public List<MindsChartDataVO> returnChartData(String currencyPair, Long periodInSeconds, Long startEpochInSeconds);
    
    public MindsCoinDataVO returnTicker(String currencyName);
    
    public List<String> returnAllMarkets();

    public MindsCompleteBalanceVO returnBalance(String currencyName);

    public MindsFeeInfoVO returnFeeInfo();
    
    public List<MindsOpenOrderVO> returnOpenOrders(String currencyName);
    
    public List<MindsTradeHistoryVO> returnTradeHistory(String currencyPair);
    
    public boolean cancelOrder(String orderNumber);
    
    public MindsOrderResultVO moveOrder(String orderNumber, BigDecimal rate, Boolean immediateOrCancel, Boolean postOnly);
    
    public MindsOrderResultVO sell(String currencyPair, BigDecimal sellPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

    public MindsOrderResultVO buy(String currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);



}
