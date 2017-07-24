package com.minds.trading.exchange.example;

import java.util.ArrayList;
import java.util.List;

import com.minds.trading.bot.tradingbot.bot.Bot;
import com.minds.trading.bot.tradingbot.bot.BotManager;
import com.minds.trading.bot.tradingbot.bot.Constants;
import com.minds.trading.bot.tradingbot.bot.SimpleBot;

public class SimpleBotRunner {

	
	public static void main(String arg[]) throws Exception
	{
		List<Bot> list = new ArrayList<>();
		SimpleBot sb = new SimpleBot(Constants.CURRENT_CURR_PAIR, 44.50075375, 1,5);
		BotManager bm = new BotManager();
		list.add(sb);
		bm.startBots(list);
		while(true)
		{
			Thread.sleep(5000000);
		}
		
	}
}
