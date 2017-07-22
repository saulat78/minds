package com.minds.trading.market;

import java.math.BigDecimal;
import java.util.List;

import com.minds.trading.market.vo.MindsCoinDataVO;
/**
 * 
 * The main interface for returning Market data.
 * Market Manager will use the MindExchanceService implementations to get the data
 * store it in the Cache and return the data when requested
 *
 */
public interface MindsMarketDatastore {

	void updatePrice(String currencyPair, MindsCoinDataVO vo);

	BigDecimal getCurrentPrice(String currencyPair);

	/**
	 * Returns the List of currency price containing the previous N values where
	 * N is equal to @param last 
	 * @param currencyPair
	 * @param last
	 * @return
	 */
	List<BigDecimal> getPreviousPrice(String currencyPair, int last);

	BigDecimal getMovingAvg(String currencyPair);

}