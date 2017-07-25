package com.minds.trading.bot.tradingbot.wallet;

import java.util.concurrent.ConcurrentHashMap;

public class MindsWallet 
{

	private ConcurrentHashMap<String, WalletEntry> entries;
	
	private static MindsWallet instance;
	
	public MindsWallet()
	{
		entries = new ConcurrentHashMap<>();
	}
	
	public synchronized static MindsWallet getInstance()
	{
		if(instance == null)
			instance = new MindsWallet();
		
		return instance;
	}	
	
	
	public WalletEntry update(String currencyPair, WalletEntry entry)
	{
		return entries.put(currencyPair, entry);
	}
	
	/**
	 * Updates the wallet entry with the new buying
	 * it will update the quantity of the coin
	 * and will average out the buying price as well
	 * @param currencyPair
	 * @param quantity
	 * @param buyingPrice
	 * @return
	 */
	public WalletEntry update(String currencyPair, int quantity, double buyingPrice)
	{
		WalletEntry entry = getEntry(currencyPair);
		if(entry == null)
		{
			entry = new WalletEntry(currencyPair, quantity, buyingPrice);
		}
		else
		{
			entry.setQuantity(entry.getQuantity() + quantity);
			double avgPrice = (entry.getAveragePrice() + buyingPrice)/quantity;
			entry.setAveragePrice(avgPrice);
		}
		return update(currencyPair, entry);
	}
	
	public WalletEntry getEntry(String currencyPair)
	{
		return entries.get(currencyPair);
	}

	@Override
	public String toString() {
		return "MindsWallet [entries=" + entries + "]";
	}
	
	
	
	
}
