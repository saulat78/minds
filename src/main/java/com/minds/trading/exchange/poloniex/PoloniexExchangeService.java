package com.minds.trading.exchange.poloniex;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.exchange.MindsExchangeService;
import com.minds.trading.exchange.PriceDataAPIClient;
import com.minds.trading.exchange.TradingAPIClient;
import com.minds.trading.exchange.poloniex.mapper.PoloniexDataMapper;
import com.minds.trading.market.vo.MindsChartDataVO;
import com.minds.trading.market.vo.MindsCoinDataVO;
import com.minds.trading.market.vo.MindsCompleteBalanceVO;
import com.minds.trading.market.vo.MindsFeeInfoVO;
import com.minds.trading.market.vo.MindsOpenOrderVO;
import com.minds.trading.market.vo.MindsOrderResultVO;
import com.minds.trading.market.vo.MindsTradeHistoryVO;



public class PoloniexExchangeService implements MindsExchangeService
{
    private final PriceDataAPIClient publicClient;
    private final TradingAPIClient tradingClient;
    private final PoloniexDataMapper mapper;
   
    private static Logger log = LoggerFactory.getLogger(PoloniexExchangeService.class);
    public PoloniexExchangeService(String apiKey, String apiSecret)
    {
        this.publicClient = new PoloniexPublicAPIClient();
        this.tradingClient = new PoloniexTradingAPIClient(apiKey, apiSecret);
        this.mapper = new PoloniexDataMapper();
    }

    public PoloniexExchangeService(PriceDataAPIClient publicClient, TradingAPIClient tradingClient, PoloniexDataMapper mapper)
    {
        this.publicClient = publicClient;
        this.tradingClient = tradingClient;
        this.mapper = mapper;
    }

    /**
     * *
     * Returns candlestick chart data for the given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param periodInSeconds The candlestick chart data period. Valid values are 300 (5 min), 900 (15 minutes), 7200 (2 hours), 14400 (4 hours), 86400 (daily)
     * @param startEpochInSeconds UNIX timestamp format and used to specify the start date of the data returned
     * @return List of PoloniexChartData
     */
    @Override
    public List<MindsChartDataVO> returnChartData(String currencyPair, Long periodInSeconds, Long startEpochInSeconds)
    {
        long start = System.currentTimeMillis();
        List<MindsChartDataVO> chartData = new ArrayList<MindsChartDataVO>();
        try
        {
            String chartDataResult = publicClient.getChartData(currencyPair, periodInSeconds, startEpochInSeconds);
            chartData = mapper.mapChartData(chartDataResult);
            log.debug("Retrieved and mapped {} chart data in {} ms", currencyPair, (System.currentTimeMillis() - start));
        }
        catch (Exception ex)
        {
        	log.error("Error retrieving chart data for {}", currencyPair, ex);
        }

        return chartData;
    }

    /**
     * *
     * Returns the ticker for all a given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return PoloniexTicker
     */
    @Override
    public MindsCoinDataVO returnTicker(String currencyPair)
    {
        long start = System.currentTimeMillis();
        MindsCoinDataVO tickerResult = null;
        try
        {
            String tickerData = publicClient.returnTicker();
            tickerResult = mapper.mapTickerForCurrency(currencyPair, tickerData);
            log.trace("Retrieved and mapped {} ticker in {} ms , price {}", currencyPair, System.currentTimeMillis() - start, tickerResult);
        }
        catch (Exception ex)
        {
            log.error("Error retrieving ticker for {} ", currencyPair, ex);
        }

        return tickerResult;
    }

    @Override
    public List<String> returnAllMarkets()
    {
        long start = System.currentTimeMillis();
        List<String> allMarkets = new ArrayList<String>();
        try
        {
            String tickerData = publicClient.returnTicker();
            allMarkets = mapper.mapMarkets(tickerData);
            log.trace("Retrieved and mapped market pairs in {} ms", System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            log.error("Error retrieving all markets -", ex);
        }

        return allMarkets;
    }

    /**
     * *
     * Returns the balance for specified currency type
     *
     * @param currencyType Examples: BTC, ETH, DASH
     * @return PoloniexCompleteBalance
     */
    @Override
    public MindsCompleteBalanceVO returnBalance(String currencyType)
    {
        long start = System.currentTimeMillis();
        MindsCompleteBalanceVO balance = null;
        try
        {
            String completeBalancesResult = tradingClient.returnCompleteBalances();
            balance = mapper.mapCompleteBalanceResultForCurrency(currencyType, completeBalancesResult);
            log.trace("Retrieved and mapped {} complete balance in {} ms", currencyType, System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            log.error("Error retrieving complete balance for {}", currencyType, ex);
        }

        return balance;
    }

    /**
     * *
     * If you are enrolled in the maker-taker fee schedule, returns your current trading fees and trailing 30-day volume in BTC. This information is updated once every 24 hours.
     *
     * @return PoloniexFeeInfo
     */
    @Override
    public MindsFeeInfoVO returnFeeInfo()
    {
        long start = System.currentTimeMillis();
        MindsFeeInfoVO feeInfo = null;
        try
        {
            String feeInfoResult = tradingClient.returnFeeInfo();
            feeInfo = mapper.mapFeeInfo(feeInfoResult);
            log.trace("Retrieved and mapped Poloniex fee info in {} ms", System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            log.error("Error retrieving fee info ", ex);
        }

        return feeInfo;
    }

   

    /**
     * *
     * Returns your open orders for a given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return List of PoloniexOpenOrder
     */
    @Override
    public List<MindsOpenOrderVO> returnOpenOrders(String currencyPair)
    {
        long start = System.currentTimeMillis();
        List<MindsOpenOrderVO> openOrders = new ArrayList<MindsOpenOrderVO>();
        try
        {
            String openOrdersData = tradingClient.returnOpenOrders(currencyPair);
            openOrders = mapper.mapOpenOrders(openOrdersData);
            log.trace("Retrieved and mapped {} {} open orders in {} ms", openOrders.size(), currencyPair, System.currentTimeMillis() - start);
            return openOrders;
        }
        catch (Exception ex)
        {
            log.error("Error retrieving open orders for {}", currencyPair, ex);
        }

        return openOrders;
    }

    /**
     * *
     * Returns up to 50,000 trades for given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return List of PoloniexTradeHistory
     */
    @Override
    public List<MindsTradeHistoryVO> returnTradeHistory(String currencyPair)
    {
        long start = System.currentTimeMillis();
        List<MindsTradeHistoryVO> tradeHistory = new ArrayList<MindsTradeHistoryVO>();
        try
        {
            String tradeHistoryData = tradingClient.returnTradeHistory(currencyPair);
            tradeHistory = mapper.mapTradeHistory(tradeHistoryData);
            log.trace("Retrieved and mapped {} {} trade history in {} ms", tradeHistory.size(), currencyPair, System.currentTimeMillis() - start);
            return tradeHistory;
        }
        catch (Exception ex)
        {
            log.error("Error retrieving trade history for {} - {}", currencyPair, ex.getMessage());
        }

        return tradeHistory;
    }

    /**
     * *
     * Places a sell order in a given market
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param sellPrice
     * @param amount
     * @param fillOrKill Will either fill in its entirety or be completely aborted
     * @param immediateOrCancel Order can be partially or completely filled, but any portion of the order that cannot be filled immediately will be canceled rather than left on the order book
     * @param postOnly A post-only order will only be placed if no portion of it fills immediately; this guarantees you will never pay the taker fee on any part of the order that fills
     * @return PoloniexOrderResult
     */
    @Override
    public MindsOrderResultVO sell(String currencyPair, BigDecimal sellPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly)
    {
        long start = System.currentTimeMillis();
        MindsOrderResultVO orderResult = null;
        try
        {
            String sellTradeResult = tradingClient.sell(currencyPair, sellPrice, amount, fillOrKill, immediateOrCancel, postOnly);
            orderResult = mapper.mapTradeOrder(sellTradeResult);
            log.trace("Executed and mapped {} sell order {} in {} ms", currencyPair, sellTradeResult, System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            log.error("Error executing sell order for {} - {}", currencyPair, ex.getMessage());
        }

        return orderResult;
    }

    /**
     * *
     * Places a buy order in a given market
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param buyPrice
     * @param amount
     * @param fillOrKill Will either fill in its entirety or be completely aborted
     * @param immediateOrCancel Order can be partially or completely filled, but any portion of the order that cannot be filled immediately will be canceled rather than left on the order book
     * @param postOnly A post-only order will only be placed if no portion of it fills immediately; this guarantees you will never pay the taker fee on any part of the order that fills
     * @return PoloniexOrderResult
     */
    @Override
    public MindsOrderResultVO buy(String currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly)
    {
        long start = System.currentTimeMillis();
        MindsOrderResultVO orderResult = null;
        try
        {
            String buyTradeResult = tradingClient.buy(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
            orderResult = mapper.mapTradeOrder(buyTradeResult);
            log.trace("Executed and mapped {} buy order {} in {} ms", currencyPair, buyTradeResult, System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            log.error("Error executing buy order for {} - {}", currencyPair, ex.getMessage());
        }

        return orderResult;
    }

    /**
     * *
     * Cancels an order you have placed in a given market
     *
     * @param orderNumber
     * @return true if successful, false otherwise
     */
    @Override
    public boolean cancelOrder(String orderNumber)
    {
        long start = System.currentTimeMillis();
        boolean success = false;
        try
        {
            String cancelOrderResult = tradingClient.cancelOrder(orderNumber);
            success = mapper.mapCancelOrder(cancelOrderResult);
            log.trace("Executed and mapped cancel order for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
            return success;
        }
        catch (Exception ex)
        {
            log.error("Error executing cancel order for {} - {}", orderNumber, ex.getMessage());
        }

        return success;
    }

    @Override
    public MindsOrderResultVO moveOrder(String orderNumber, BigDecimal rate, Boolean immediateOrCancel, Boolean postOnly)
    {
        long start = System.currentTimeMillis();
        MindsOrderResultVO orderResult = null;
        try
        {
            String moveOrderResult = tradingClient.moveOrder(orderNumber, rate);
            orderResult = mapper.mapTradeOrder(moveOrderResult);
            log.trace("Executed and mapped move order for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
        	log.error("Error executing move order for {} - {}", orderNumber, ex.getMessage());
        }

        return orderResult;
    }
}
