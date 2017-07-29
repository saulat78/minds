package com.minds.trading.bot.tradingbot.bot;

import java.util.List;

import com.minds.trading.bot.tradingbot.MarketWarmup;

public class BotManager
{

	private List<Bot> bots = null;
	
	
	//TODO make it singleton
	public BotManager()
	{
		MarketWarmup marketStarter = new MarketWarmup();
		marketStarter.init();
	}
	
	public void startBots(List<Bot> list)
	{
		this.bots = list;
		
		for(Bot bot:bots)
		{
			BotRunner runner = new BotRunner();
			runner.runBot(bot);
		}
	}
	
}
