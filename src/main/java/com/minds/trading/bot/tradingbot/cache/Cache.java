package com.minds.trading.bot.tradingbot.cache;

import java.util.Set;

//	K is for the data's key type
//	V is for the data's type
public interface Cache<S, K, V> {
	public CacheSubject<K, V> forSubject(S subject);

	public boolean addSubject(S subjectKey, CacheSubject<K, V> subject);
	
	public Set<S> getSubjects();
}
