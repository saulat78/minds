package com.minds.trading.market;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import com.minds.trading.market.vo.MindsCoinDataVO;

public class MindsMarketDatastoreImpl implements  MindsMarketDatastore
{
	private Cache lastHourCoinPrice;
	private Cache currentCoinPrice;

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
		CacheManager cm = CacheManager.newInstance();

		//2. Get a cache called "cache1", declared in ehcache.xml
		 lastHourCoinPrice = cm.getCache("marketDataCache");
		 currentCoinPrice = cm.getCache("currentPriceCache");
	
    }
	
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#updatePrice(java.lang.String, com.minds.trading.market.vo.MindsCoinDataVO)
	 */
	@Override
	public void updatePrice(String currencyPair, MindsCoinDataVO vo)
	{
		this.currentCoinPrice.put(new Element(currencyPair, vo.last));
		
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
		Element ele = this.currentCoinPrice.get(currencyPair);
		return (BigDecimal) (ele == null ? null : ele.getObjectValue());
	}
	
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getPreviousPrice(java.lang.String, int)
	 */
	@Override
	public List<BigDecimal> getPreviousPrice(String currencyPair, int last)
	{
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
	}
	
	/* (non-Javadoc)
	 * @see com.minds.trading.market.MindsMarketManager#getMovingAvg(java.lang.String)
	 */
	@Override
	public BigDecimal getMovingAvg(String currencyPair)
	{
		Element ele = lastHourCoinPrice.get(currencyPair);
		List<MindsCoinDataVO> list = (LinkedList<MindsCoinDataVO>) (ele == null ? null : ele.getObjectValue());
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
