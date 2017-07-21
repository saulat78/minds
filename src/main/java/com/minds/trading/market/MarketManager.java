package com.minds.trading.market;

import java.math.BigDecimal;

/**
 * 
 * The main interface for returning Market data.
 * Market Manager will use the MindExchanceService implementations to get the data
 * store it in the Cache and return the data when requested
 *
 */
public interface MarketManager
{

  public BigDecimal getLastPrice(String exchange, String currencyPair);
	
}
