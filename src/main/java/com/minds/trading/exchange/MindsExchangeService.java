package com.minds.trading.exchange;

import java.math.BigDecimal;
import java.util.List;

import com.minds.trading.bot.tradingbot.bot.Constants;
import com.minds.trading.market.vo.MindsChartDataVO;
import com.minds.trading.market.vo.MindsCoinDataVO;
import com.minds.trading.market.vo.MindsCompleteBalanceVO;
import com.minds.trading.market.vo.MindsFeeInfoVO;
import com.minds.trading.market.vo.MindsOpenOrderVO;
import com.minds.trading.market.vo.MindsOrderResultVO;
import com.minds.trading.market.vo.MindsTradeHistoryVO;



public interface MindsExchangeService extends Constants
{
  
    
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
