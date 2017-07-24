package com.minds.trading.bot.tradingbot.bot;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.market.MindsMarketDatastore;
import com.minds.trading.market.MindsMarketDatastoreImpl;

public class SimpleBot implements Bot
{

	private String currencyPair = null;
	private MindsMarketDatastore datastore = MindsMarketDatastoreImpl.getInstance();
	private DecimalFormat df = new DecimalFormat("#.####");
	private final static Logger log = LoggerFactory.getLogger(SimpleBot.class);
	private double buyPriceLimit = -1;
	private int perTransactionBuyingQty = -1;
	private double feePercent = 0.50; //includes buying and selling
	private int totalBuyingQty =-1;
	
	public SimpleBot(String currencyPair,double buyPriceLimit, int perTransactionBuyingQty, int totalBuyingQty )
	{
		this.currencyPair = currencyPair;
		this.buyPriceLimit = buyPriceLimit;
		this.perTransactionBuyingQty = perTransactionBuyingQty;
		this.totalBuyingQty = totalBuyingQty;
		 df.setRoundingMode(RoundingMode.CEILING);
		 log.info("Simple Bot started for " + currencyPair+", buyPriceLimit " + this.buyPriceLimit + ",  perTransactionBuyingQty " + perTransactionBuyingQty + ", totalBuyingQty " + totalBuyingQty );


	}
	
	@Override
	public int runInterval() 
	{
		
		return 1;
	}

	@Override
	public boolean run()
	{
	
		try
		{
			 Thread.sleep(3000);
				 double prevPrice = datastore.getCurrentPrice(currencyPair).doubleValue();
				 Thread.sleep(1000);
				 double currPrice =  datastore.getCurrentPrice(currencyPair).doubleValue();
				 if(prevPrice != currPrice)
				 {
					 double percChangePrevPrice = ((currPrice - prevPrice)/prevPrice)*100;
					 double percChangeBuyingPrice = ((currPrice - this.buyPriceLimit)/this.buyPriceLimit)*100;
					 log.info(currencyPair+":" + currPrice + " BuyingPriceChange " + percChangeBuyingPrice + "%  Change=" + df.format(percChangePrevPrice) + "%" );
				 }
			}
		 catch(Exception e)
		 {
			 log.error("Exception in SimpleBot " , e);
		 }
		return true;
	}

	private void buy(double currentPrice)
	{
		if(currentPrice <= this.buyPriceLimit)
		{
			double totalbuyingPrice = this.perTransactionBuyingQty * currentPrice;
			double minSellingPrice =  (totalbuyingPrice * this.feePercent) /100;
			log.info("SimpleBot bought " + this.perTransactionBuyingQty + " each for " +  currentPrice + " total amt " + totalbuyingPrice + " selling price is " + minSellingPrice );
			//TODO add all the bought coin into a list and iterate over it in the sell method to check what to sell
		}
	}
	
	private void sell()
	{
		
	}
	@Override
	public void stop()
	{
		
		
	}

}
