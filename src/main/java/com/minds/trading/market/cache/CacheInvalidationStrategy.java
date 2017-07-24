package com.minds.trading.market.cache;

public interface CacheInvalidationStrategy<S, K, V> {

	public void invalidate(Cache<S, K, V> cache);
}
