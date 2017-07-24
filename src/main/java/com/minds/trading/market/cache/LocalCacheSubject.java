package com.minds.trading.market.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	@Override
	public Set<K> getKeys() {
		return map.keySet();
	}

	@Override
	public boolean remove(K key) {
		if (map.containsKey(key)) {
			map.remove(key);
			return true;
		}
		return false;
	}

}
