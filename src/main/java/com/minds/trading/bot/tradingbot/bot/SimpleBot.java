package com.minds.trading.bot.tradingbot.bot;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.market.MindsMarketDatastore;
import com.minds.trading.market.MindsMarketDatastoreImpl;

public class SimpleBot implements Bot
{

	private String currencyPair = null;
	private MindsMarketDatastore datastore = MindsMarketDatastoreImpl.getInstance();
	private DecimalFormat df = new DecimalFormat("#.####");
	private final static Logger log = LoggerFactory.getLogger(SimpleBot.class);

	public SimpleBot(String currencyPair)
	{
		this.currencyPair = currencyPair;
		 df.setRoundingMode(RoundingMode.CEILING);

	}
	
	@Override
	public int runInterval() 
	{
		
		return 1;
	}

	@Override
	public boolean run()
	{
	
		try
		{
			 Thread.sleep(2000);
			 log.info(currencyPair +" Current Price : " + datastore.getCurrentPrice(currencyPair));
			
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
		 catch(Exception e)
		 {
			 log.error("Exception in SimpleBot " , e);
		 }
		return true;
	}

	@Override
	public void stop() {
		
		
	}

}
