package com.minds.trading.market;

import com.minds.trading.exchange.ExchangeFactory;
import com.minds.trading.exchange.MindsExchangeService;
import com.minds.trading.market.worker.MarketWorker;
import com.minds.trading.market.worker.PriceUpdateWorker;

/**
 * 
 * Market Manager Implementation
 *
 */
public class MarketManagerImpl 
{

	private static MarketManagerImpl instance = null;
	private MarketWorker priceUpdateWorker = null;
	private MindsMarketDatastore datastore = null;
	private MindsExchangeService service = null;
	
	public synchronized static MarketManagerImpl getInstance()
	{
		if(instance == null)
			instance = new MarketManagerImpl();
		
		return instance;
	}

	private MarketManagerImpl()
	{
		this.datastore = MindsMarketDatastoreImpl.getInstance();
		this.service = ExchangeFactory.getExchangeService(ExchangeFactory.POLONIEX_SERVICE);
	}
	
	public void startWorkers()
	{
		 this.priceUpdateWorker = new PriceUpdateWorker(1l, this.datastore, this.service);
		 this.priceUpdateWorker.init();
	}
	
	public void stopWorkers()
	{
		this.priceUpdateWorker.stopWork();
	}
}
