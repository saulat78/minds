package com.minds.trading.market.cache;


public interface CacheManager<S, K, V> {
	public Cache<S, K, V> getCache();
	
	public boolean addSubject(S subjectKey, CacheSubject<K, V> subject);
}
