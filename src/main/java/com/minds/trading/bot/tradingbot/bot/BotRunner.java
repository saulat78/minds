package com.minds.trading.bot.tradingbot.bot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotRunner
{
	
	private ScheduledExecutorService executorService = null;
	public void runBot(Bot bot)
	{
		 executorService = Executors.newSingleThreadScheduledExecutor();
		 executorService.scheduleAtFixedRate(new Runnable() {
			    @Override
			    public void run() 
			    {
			       bot.run();
			    }
			}, 0, bot.runInterval(), TimeUnit.SECONDS);
	}
}
