package com.minds.trading.exchange.example;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.exchange.ExchangeFactory;
import com.minds.trading.exchange.MindsExchangeService;
import com.minds.trading.exchange.poloniex.PoloniexExchangeService;
import com.minds.trading.market.MarketManagerImpl;
import com.minds.trading.market.MindsMarketDatastore;
import com.minds.trading.market.MindsMarketDatastoreImpl;
import com.minds.trading.market.vo.MindsChartDataVO;

public class StartBasicStrategy {

	  private final static Logger log = LoggerFactory.getLogger(StartBasicStrategy.class);
	
	   

	   
	    
	 public void run() throws Exception
	 {
		 String currencyPair = "BTC_ETH";
		 MarketManagerImpl market = MarketManagerImpl.getInstance();
		 market.startWorkers();
		 Thread.sleep(1000);
		 MindsMarketDatastore datastore = MindsMarketDatastoreImpl.getInstance();
		 while(true)
		 {
			 Thread.sleep(1000);
		log.info(currencyPair+":"+datastore.getCurrentPrice(currencyPair) + ", " + datastore.getPreviousPrice(currencyPair, 3));
		 }
		
	 }
	public static void main(String arg[]) throws Exception
	{
	  
	   new StartBasicStrategy().run();
	   Thread.currentThread().sleep(500000);
	}
}
