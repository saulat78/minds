package com.minds.trading.exchange.example;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.exchange.MindsExchangeService;
import com.minds.trading.market.MarketManagerImpl;
import com.minds.trading.market.MindsMarketDatastore;
import com.minds.trading.market.MindsMarketDatastoreImpl;

public class StartBasicStrategy
{

    private final static Logger log = LoggerFactory.getLogger(StartBasicStrategy.class);
	public void run() throws Exception
	 {
		 String currencyPair =  MindsExchangeService.CURRENT_CURR_PAIR;
		 MarketManagerImpl market = MarketManagerImpl.getInstance();
		 market.startWorkers();
		 Thread.sleep(1000);
		 MindsMarketDatastore datastore = MindsMarketDatastoreImpl.getInstance();
		 DecimalFormat df = new DecimalFormat("#.####");
		 df.setRoundingMode(RoundingMode.CEILING);
		 while(true)
		 {
			 double prevPrice = datastore.getCurrentPrice(currencyPair).doubleValue();
			 Thread.sleep(1000);
			 double currPrice =  datastore.getCurrentPrice(currencyPair).doubleValue();
			 if(prevPrice != currPrice)
			 {
				 double percChange = ((currPrice - prevPrice)/prevPrice)*100;
				 log.info(currencyPair+":" + currPrice + "  Change=" + df.format(percChange) + "%" );
			 }
		 }
		
	 }
	public static void main(String arg[]) throws Exception
	{
	  
	   new StartBasicStrategy().run();
	   Thread.currentThread().sleep(500000);
	}
}
