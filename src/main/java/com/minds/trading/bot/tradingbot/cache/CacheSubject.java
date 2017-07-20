package com.minds.trading.bot.tradingbot.cache;

public interface CacheSubject<K, V> {
	
	public V forKey(K key);
	
	public boolean put(K key, V value);
}