package com.minds.trading.bot.tradingbot.bot;

import java.util.List;

public class BotManager
{

	private List<Bot> bots = null;
	
	
	//TODO make it singleton
	public BotManager()
	{
		
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
