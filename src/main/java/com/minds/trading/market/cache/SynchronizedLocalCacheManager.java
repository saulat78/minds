package com.minds.trading.market.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//	LocalCache implementation of CacheManager
@Component
public class SynchronizedLocalCacheManager<S, K, V> implements CacheManager<S, K, V> {

	 private final static Logger log = LoggerFactory.getLogger(SynchronizedLocalCacheManager.class);

	Cache<S, K, V> localCache = null;
	CacheInvalidationStrategy<S, K, V> dateTimeInvalidationStrategy = null;

	@Override
	public Cache<S, K, V> getCache() {
		if (localCache == null) {
			localCache = new LocalCache<S, K, V>();
			// Start the invalidation thread
			startInvalidationThread();
		}
		synchronized (localCache) {
			return localCache;
		}
	}

	@Override
	public boolean addSubject(S subjectKey, CacheSubject<K, V> subject) {
		//	Get cache already synchronized
		return getCache().addSubject(subjectKey, subject);
	}

	@Override
	public CacheInvalidationStrategy<S, K, V> getCacheInvalidationStrategy() {
		if (dateTimeInvalidationStrategy == null) {
			dateTimeInvalidationStrategy = new DateTimeInvalidationStrategy<S, K, V>();
		}
		return dateTimeInvalidationStrategy;
	}
	
	//	This method invalidates the cache every 60 seconds
	public void startInvalidationThread() {
		(new Thread() {
			public void run() {
				try {
					Thread.sleep(60000);
					getCacheInvalidationStrategy().invalidate(localCache);
				} catch (Exception e) {
					log.error("Error invalidating local cache");
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}).start();
	}
}
