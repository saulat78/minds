package com.minds.trading.market;

import java.util.Date;

import com.minds.trading.market.cache.CacheManager;
import com.minds.trading.market.vo.MindsCoinDataVO;

/**
 * 
 * Stores the Market Data
 * Internally uses CacheManager to store data
 * */
public class MarketData 
{

	private CacheManager<String, Date, MindsCoinDataVO> coinCurrentDataManager;

	
	
}
