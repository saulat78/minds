package com.minds.trading.market;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.minds.trading.market.cache.CacheSubject;
import com.minds.trading.market.vo.MindsCoinDataVO;

//	TODO: Convert this into an autowireable bean to be able to autowire cachemanager
public class MindsMarketDatastoreImpl implements  MindsMarketDatastore
{
	
	@SuppressWarnings("unchecked")
	//	Static ((non autowired) cacheManagerInstance												//	String is coin name, Date is time when inserted, Double is market value
	com.minds.trading.market.cache.CacheManager<String, Date, BigDecimal> cacheManager = com.minds.trading.market.cache.SynchronizedLocalCacheManager.getLocalInstance();
	
	//	TODO: Convert these to use the localcache
	//private Cache lastHourCoinPrice;
	//private Cache currentCoinPrice;

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
		//CacheManager cm = CacheManager.newInstance();

		//2. Get a cache called "cache1", declared in ehcache.xml
		 //lastHourCoinPrice = cm.getCache("marketDataCache");
		 //currentCoinPrice = cm.getCache("currentPriceCache");
	
    }
	
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#updatePrice(java.lang.String, com.minds.trading.market.vo.MindsCoinDataVO)
	 */
	@Override
	public void updatePrice(String currencyPair, MindsCoinDataVO vo)
	{
		cacheManager.getCache().forSubject(currencyPair).put(new Date(), vo.last);
		//this.currentCoinPrice.put(new Element(currencyPair, vo.last));
		
	/*	Element ele = lastHourCoinPrice.get(currencyPair);
		
		LinkedList<MindsCoinDataVO> list = (LinkedList<MindsCoinDataVO>) (ele == null ? null : ele.getObjectValue());
		if(list == null)
		{
			list = new LinkedList<MindsCoinDataVO>();
		}
		list.addFirst(vo);
		this.lastHourCoinPrice.put(new Element(currencyPair, list));
		*/
	}
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getCurrentPrice(java.lang.String)
	 */
	@Override
	public BigDecimal getCurrentPrice(String currencyPair)
	{
		return cacheManager.getCache().forSubject(currencyPair).getLatest();
		//Element ele = this.currentCoinPrice.get(currencyPair);
		//return (BigDecimal) (ele == null ? null : ele.getObjectValue());
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getPreviousPrice(java.lang.String, int)
	 */
	@Override
	public List<BigDecimal> getPreviousPrice(String currencyPair, int n)
	{
		CacheSubject<Date, BigDecimal> cacheSubject = cacheManager.getCache().forSubject(currencyPair);
		Set<Date> dateKeys = cacheSubject.getKeys();
		
		//	Sort by date
		List<Date> sortedDates = new ArrayList<Date>(dateKeys);
		java.util.Collections.sort(sortedDates);
		
		//	Get n dates by trimming the first size-n elements
		//	I am avoiding removing at a particular element (other than first) to avoid array out of bounds exceptions
		for (int i = 0; i < sortedDates.size() - n; i++) {
			sortedDates.remove(0);
		}
		
		//	return values for those dates
		List<BigDecimal> values = new ArrayList<BigDecimal>();
		for (Date key : sortedDates) {
			values.add(cacheSubject.forKey(key));
		}
		
		return values;
		
		/*
		Element ele = lastHourCoinPrice.get(currencyPair);
		List<MindsCoinDataVO> list =  (LinkedList<MindsCoinDataVO>) (ele == null ? null : ele.getObjectValue());
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
		*/
	}
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getMovingAvg(java.lang.String)
	 */
	@Override
	public BigDecimal getMovingAvg(String currencyPair)
	{
		//Element ele = lastHourCoinPrice.get(currencyPair);
		//List<MindsCoinDataVO> list = (LinkedList<MindsCoinDataVO>) (ele == null ? null : ele.getObjectValue());
		
		Set<BigDecimal> data = cacheManager.getCache().forSubject(currencyPair).getValues();
		
		if(data.size() == 0)
			return null;
		
		BigDecimal sum = new BigDecimal(0);
		for(BigDecimal val : data)
		{
			sum = sum.add(val);
		}
		return sum.divide(new BigDecimal(data.size()));
	}
}
