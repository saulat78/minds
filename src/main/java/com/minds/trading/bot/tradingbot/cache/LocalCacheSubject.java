package com.minds.trading.bot.tradingbot.cache;

import java.util.HashMap;
import java.util.Map;

//	Hashmap implementation of CacheSubject
public class LocalCacheSubject<K, V> implements CacheSubject<K, V> {

	private Map<K, V> map = new HashMap<K, V>();
	
	@Override
	public V forKey(K key) {
		return map.get(key);
	}

	@Override
	public boolean put(K key, V value) {
		if (map.containsKey(key))
			return false;
		map.put(key, value);
		return true;
	}
	
	@Override
	public String toString() {
		String ret = "{";
		
		for (K key : map.keySet()) {
			ret += key + "=" + map.get(key) + ", ";
		}
		
		ret += "}";
		return ret;
	}

}
