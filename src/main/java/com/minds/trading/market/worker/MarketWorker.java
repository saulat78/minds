package com.minds.trading.market.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * 
 * The base class for every MarketWorker. Each Market worker will be responsible for 
 * specific tasks like updating the currency values to the MarketData, calculating and storing
 * Moving Averages etc
 * */
public abstract class MarketWorker  
{
	private ScheduledExecutorService executorService = null;
	private long interval;
	public MarketWorker(long interval)
	{
		this.interval = interval;
	}
	public void init()
	{
		    executorService = Executors.newSingleThreadScheduledExecutor();
		    executorService.scheduleAtFixedRate(new Runnable() {
			    @Override
			    public void run() 
			    {
			        doWork();
			    }
			}, 0, interval, TimeUnit.SECONDS);
		
			
	}
	
	public abstract void doWork();
	public void stopWork()
	{
		executorService.shutdownNow();
	}
}
