package com.minds.trading.exchange.example;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.exchange.MindsExchangeService;
import com.minds.trading.exchange.poloniex.PoloniexExchangeService;
import com.minds.trading.market.vo.MindsCoinDataVO;
import com.minds.trading.market.vo.MindsCompleteBalanceVO;
import com.minds.trading.market.vo.MindsOpenOrderVO;
import com.minds.trading.market.vo.MindsOrderResultVO;
import com.minds.trading.market.vo.MindsTradeHistoryVO;

//	WARNING, THIS WILL LOOSE MONEY GURANTEED
//	DO NOT RUN THIS CLASS
public class ExchangeExamples {

    private final static Logger log = LoggerFactory.getLogger(ExchangeExamples.class);
    private final static String DEFAULT_PROPERTIES_FILE = "exchange.properties";
    private final static String POLONIEX_API_KEY_PROP_NAME = "poloniex.api.key";
    private final static String POLONIEX_API_SECRET_PROP_NAME = "poloniex.api.secret";

	public static void main(String[] args) {
        String propertiesFileName = args.length > 0 ? args[0] : DEFAULT_PROPERTIES_FILE;
        try {
			new ExchangeExamples().run(propertiesFileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}        
	}
	
	public void run(String propertiesFileName) throws Throwable {
        Properties properties = this.loadProperties(propertiesFileName);
        
        String tradingAPIKey = properties.getProperty(POLONIEX_API_KEY_PROP_NAME);
        if (tradingAPIKey == null)
        {
            log.warn("Did not find value for " + POLONIEX_API_KEY_PROP_NAME + " in " + propertiesFileName + ". Trading API commands will fail");
        }

        String tradingAPISecret = properties.getProperty(POLONIEX_API_SECRET_PROP_NAME);
        if (tradingAPISecret == null)
        {
            log.warn("Did not find value for " + POLONIEX_API_SECRET_PROP_NAME + " in " + propertiesFileName + ". Trading API commands will fail");
        }

        MindsExchangeService service = new PoloniexExchangeService(tradingAPIKey, tradingAPISecret);

        //	1) Get the ticker price for a coin
        log.info("Price for " + PoloniexExchangeService.DGB_BTC_CURRENCY_PAIR);
        MindsCoinDataVO price = service.returnTicker(PoloniexExchangeService.DGB_BTC_CURRENCY_PAIR);
        log.info(price.toString());
        log.info("");
        
        //	2) Get all balances
        log.info("Balances:");
        MindsCompleteBalanceVO balance = service.returnBalance("");
        log.info("");

        //	3) Buy a coin (open an order)
        	//	Buy for very low to keep the order alive until we can get our open orders, and cancel the order
        	//	Buy 0.0002 worth of bitcoin for a VERY low price
        MindsOrderResultVO lowBuy = service.buy(PoloniexExchangeService.DGB_BTC_CURRENCY_PAIR, new BigDecimal(0.00000005), new BigDecimal(4000.0), false, false, false);
        log.info("Low Buy:");
        log.info(lowBuy.toString());
        log.info("");
        
        Thread.sleep(2000);
        
        //	4) Get open orders
        log.info("Open Orders:");
        List<MindsOpenOrderVO> openOrders = service.returnOpenOrders(PoloniexExchangeService.DGB_BTC_CURRENCY_PAIR);
        for (MindsOpenOrderVO openOrder : openOrders) {
        	log.info(openOrder.toString());
        }
        log.info("");
        
        //	5) Cancel an order
        boolean cancelOrderResult = service.cancelOrder(lowBuy.orderNumber+"");
        log.info("Cancel low buy result: " + cancelOrderResult);
        
        //	6) Buy another coin for SLIGHTLY over the current price (overpay)
       double amt = 1/price.highestBid.doubleValue() * .0002;
        
        MindsOrderResultVO highBuy = service.buy(PoloniexExchangeService.DGB_BTC_CURRENCY_PAIR, price.highestBid, new BigDecimal(amt), true, false, false);
        log.info("BUYING " + amt + " FOR " + price.highestBid.doubleValue() );
        log.info("High Buy:");
        log.info(highBuy.toString());
        
        Thread.sleep(2000);
        
        //	7) Sell the coin for SLIGHTLY under the current price (undersell)
        MindsOrderResultVO lowSell = service.sell(PoloniexExchangeService.DGB_BTC_CURRENCY_PAIR, price.lowestAsk, new BigDecimal(amt), true, false, false);
        log.info("SELLING " + amt + " FOR " + price.lowestAsk.doubleValue() );
        log.info("Low Sell:");
        log.info(lowSell.toString());

        Thread.sleep(2000);
        
        //	8) Get the trade history
        List<MindsTradeHistoryVO> tradeHistory = service.returnTradeHistory(PoloniexExchangeService.DGB_BTC_CURRENCY_PAIR);
        log.info("Trade History:");
        for (MindsTradeHistoryVO history : tradeHistory) {
        	log.info(history.toString());
        }
	}
	
    private Properties loadProperties(String propertiesFileName)
    {
        Properties properties = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName))
        {
            properties.load(in);
        }
        catch (IOException ex)
        {
            log.error("Could not load properties file " + propertiesFileName + " - " + ex.getMessage());
        }

        return properties;
    }
}
