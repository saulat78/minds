package com.minds.trading.bot.tradingbot.cache;

import org.springframework.stereotype.Component;

//	LocalCache implementation of CacheManager
@Component
public class LocalCacheManager<S, K, V> implements CacheManager<S, K, V> {

	Cache<S, K, V> localCache = null;
	
	@Override
	public Cache<S, K, V> getCache() {
		if (localCache == null)
			localCache = new LocalCache<S, K, V>();
		return localCache;
	}

	@Override
	public boolean addSubject(S subjectKey, CacheSubject<K, V> subject) {
		return getCache().addSubject(subjectKey, subject);
	}
}
