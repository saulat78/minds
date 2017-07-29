package com.minds.trading.market.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//	HashMap implementation of Cache
public class LocalCache<S, K, V> implements Cache<S, K, V> {

	private Map<S, CacheSubject<K, V>> subjects = new HashMap<S, CacheSubject<K, V>>();
	
	@Override
	public CacheSubject<K, V> forSubject(S subjectKey) {
		if (subjects.get(subjectKey) == null)
			addSubject(subjectKey, new LocalCacheSubject<K, V>());
		return subjects.get(subjectKey);

	}

	@Override
	public boolean addSubject(S subjectKey, CacheSubject<K, V> subject) {
		if (subjects.containsKey(subjectKey))
			return false;
		subjects.put(subjectKey, subject);
		return true;
	}

	@Override
	public Set<S> getSubjectKeys() {
		return subjects.keySet();
	}
	
	@Override
	public String toString() {
		String ret = "[";
		
		for (S subjectKey : subjects.keySet()) {
			ret += subjectKey + "=" + forSubject(subjectKey) + ", \n"; 
		}
		
		ret += "]";
		return ret;
	}

}
