package com.minds.trading.market.cache;

import java.util.Set;

public interface CacheSubject<K, V> {
	
	public V forKey(K key);
	
	public Set<K> getKeys();
	
	public Set<V> getValues();
	
	public boolean put(K key, V value);
	
	public boolean remove(K key);
	
	public V getLatest();
}