package com.minds.trading.bot.tradingbot.bot;

public interface Bot {

	public int runInterval();
	

	
	/**
	 * runs the bot
	 * returns if the bot needs to run again
	 * */
	public boolean run();
	
	public void stop();
	
}
