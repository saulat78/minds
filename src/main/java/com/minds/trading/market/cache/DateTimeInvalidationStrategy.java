package com.minds.trading.market.cache;

import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//	The date-time implementation of CacheInvalidationStrategy
//	If the cache key is Date and the date is more than 10 minutes old, remove it from the cache
public class DateTimeInvalidationStrategy<S, K, V> implements
		CacheInvalidationStrategy<S, K, V> {

	 private final static Logger log = LoggerFactory.getLogger(DateTimeInvalidationStrategy.class);

	@Override
	public void invalidate(Cache<S, K, V> cache) {
		for (S subjectKey : cache.getSubjectKeys()) {
			CacheSubject<K, V> subject = cache.forSubject(subjectKey);
			for (Iterator<K> iterator = subject.getKeys().iterator(); iterator.hasNext(); ) {
			    K key = iterator.next();
				try {
					Date dateKey = (Date) key;
					//	Current date minus 10 minutes
					Date invalidationPoint = new Date();
					Long invalidationEpoch = invalidationPoint.getTime() - 600000;
					invalidationPoint.setTime(invalidationEpoch);
					//	If this entry has expired...
					if (dateKey.before(invalidationPoint)) {
						//	Remove it from the cache
						iterator.remove();
					}
				} catch (ClassCastException ex) {
					log.error("DateTimeInvalidationStrategy Cannot invaliate cache entry " + subjectKey + " :: " + key);
				}
			}
		}
	}
}
