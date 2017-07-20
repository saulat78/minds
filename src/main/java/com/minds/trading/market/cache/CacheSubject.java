package com.minds.trading.market.cache;

public interface CacheSubject<K, V> {
	
	public V forKey(K key);
	
	public boolean put(K key, V value);
}