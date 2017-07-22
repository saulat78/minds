package com.minds.trading.market.worker;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import com.minds.trading.exchange.ExchangeFactory;
import com.minds.trading.exchange.MindsExchangeService;
import com.minds.trading.exchange.poloniex.PoloniexExchangeService;
import com.minds.trading.market.MindsMarketDatastore;
import com.minds.trading.market.vo.MindsChartDataVO;
import com.minds.trading.market.vo.MindsCoinDataVO;

public class PriceUpdateWorker extends MarketWorker
{

	private MindsMarketDatastore datastore;
	private MindsExchangeService service;
	public PriceUpdateWorker(long interval, MindsMarketDatastore datastore,  MindsExchangeService service)
	{
		super(interval);
		this.datastore = datastore;
		this.service = service;
		
	}

	public void init()
	{
		super.init();
		

	}

	@Override
	public void doWork()
	{
			String currencyPair = "BTC_ETH";
		   Long yesterdayEpochSecond = ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toEpochSecond();
		       MindsCoinDataVO vo =  service.returnTicker(currencyPair);
	       if(vo != null)
	    	   this.datastore.updatePrice(currencyPair, vo);
	}
}
