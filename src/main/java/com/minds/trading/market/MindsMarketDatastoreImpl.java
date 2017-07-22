package com.minds.trading.market;

import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.xml.XmlConfiguration;


import com.minds.trading.market.vo.MindsCoinDataVO;

public class MindsMarketDatastoreImpl implements  MindsMarketDatastore
{
	private Cache<String,LinkedList> lastHourCoinPrice;
	private Cache<String,BigDecimal> currentCoinPrice;

	private static MindsMarketDatastore instance=null;
	
	private MindsMarketDatastoreImpl()
	{
		this.init();
	}


	public synchronized static MindsMarketDatastore getInstance()
	{
		if(instance == null)
			instance = new MindsMarketDatastoreImpl();
		
		return instance;
	}

	public void init()
	{
		 Configuration xmlConfig = new XmlConfiguration(MindsMarketDatastoreImpl.class.getResource("/ehcache.xml"));
		    try (CacheManager cacheManager = newCacheManager(xmlConfig))
		    {
		      cacheManager.init();
		      lastHourCoinPrice = cacheManager.getCache("marketDataCache", String.class, LinkedList.class);
		      currentCoinPrice = cacheManager.getCache("currentPriceCache", String.class, BigDecimal.class);
		    }
    }
	
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#updatePrice(java.lang.String, com.minds.trading.market.vo.MindsCoinDataVO)
	 */
	@Override
	public void updatePrice(String currencyPair, MindsCoinDataVO vo)
	{
		this.currentCoinPrice.put(currencyPair, vo.last);
		LinkedList<MindsCoinDataVO> list =  lastHourCoinPrice.get(currencyPair);
		if(list == null)
		{
			list = new LinkedList<MindsCoinDataVO>();
		}
		list.addFirst(vo);
		this.lastHourCoinPrice.put(currencyPair, list);
		
	}
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getCurrentPrice(java.lang.String)
	 */
	@Override
	public BigDecimal getCurrentPrice(String currencyPair)
	{
		return this.currentCoinPrice.get(currencyPair);
	}
	
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getPreviousPrice(java.lang.String, int)
	 */
	@Override
	public List<BigDecimal> getPreviousPrice(String currencyPair, int last)
	{
		List<MindsCoinDataVO> list =  lastHourCoinPrice.get(currencyPair);
		if(list == null)
			return null;
		List<BigDecimal> toReturn = new ArrayList<>(3);
		int totalSize = list.size();
		for(int i=0;i<last;i++)
		{
			if(totalSize > i)
				toReturn.add(list.get(i).last);
		}
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getMovingAvg(java.lang.String)
	 */
	@Override
	public BigDecimal getMovingAvg(String currencyPair)
	{
		List<MindsCoinDataVO> list =  lastHourCoinPrice.get(currencyPair);
		if(list == null)
			return null;
		
		BigDecimal sum = new BigDecimal(0);
		for(MindsCoinDataVO vo : list)
		{
			sum = sum.add(vo.last);
		}
		return sum.divide(new BigDecimal(list.size()));
	}
}
