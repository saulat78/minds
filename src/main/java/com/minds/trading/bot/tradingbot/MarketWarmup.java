package com.minds.trading.bot.tradingbot;


import com.minds.trading.market.MarketManagerImpl;


public class MarketWarmup {


	private MarketManagerImpl market = null;
	//TODO make it singleton
	public MarketWarmup()
	{
		
	}
	public void init()
	{
		 market = MarketManagerImpl.getInstance();
		 market.startWorkers();
	}
	
	
}
