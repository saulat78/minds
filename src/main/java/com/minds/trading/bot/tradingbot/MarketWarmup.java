package com.minds.trading.bot.tradingbot;


import com.minds.trading.market.MarketManagerImpl;


public class MarketWarmup {


	private MarketManagerImpl market = null;

	public void init()
	{
		 market = MarketManagerImpl.getInstance();
		 market.startWorkers();
	}
	
	
}
