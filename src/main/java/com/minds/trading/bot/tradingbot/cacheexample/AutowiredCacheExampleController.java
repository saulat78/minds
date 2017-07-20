package com.minds.trading.bot.tradingbot.cacheexample;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.minds.trading.market.cache.CacheManager;
import com.minds.trading.market.cache.LocalCacheSubject;

@Controller
public class AutowiredCacheExampleController {

	/*
	 * In this example, we can see how the generic autowire-able cache manager works
	 * 
	 * The cache manager basically gives access to a cache, which is basically a double hashmap
	 * 
	 * The first generic type (String) refers to the subject's type.
	 * in this case, the subject is the coin's name
	 * 
	 * The second generic type refers to the actual data's key
	 * in this case, the data's key is Date type, representing when the data was fetched from the API
	 * 
	 * The last generic type is the actual data type
	 * in this case, the data type is some dummy inner object.
	 * In reality, the data type would be the data type we get from the API
	 * 
	 * In the testCachePut method, we put some dummy data into the cache by coin name, by date
	 * This basically means, for some coin, for some date, here is the data.
	 * 
	 * In the testCacheGet method, we get the BTC data
	 * 
	 */
	
	@Autowired
	CacheManager<String, Date, CoinData> cacheManager;

	@RequestMapping(value = "testCachePut.html", method = RequestMethod.GET)
	public ModelAndView testCachePut() {
		
		//	Add the subjects to the cache
		cacheManager.addSubject("BTC", new LocalCacheSubject<Date, CoinData>());
		cacheManager.addSubject("ETH", new LocalCacheSubject<Date, CoinData>());
		
		//	Add some data to the cache
		cacheManager.getCache().forSubject("BTC").put(new Date(), new CoinData(1.01f));
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		cacheManager.getCache().forSubject("BTC").put(new Date(), new CoinData(1.05f));
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		cacheManager.getCache().forSubject("ETH").put(new Date(), new CoinData(.0101f));
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		cacheManager.getCache().forSubject("ETH").put(new Date(), new CoinData(.0105f));
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data");
		mav.addObject("data", "put some data...");
		return mav;
	}
	
	@RequestMapping(value = "testCacheGet.html", method = RequestMethod.GET)
	public ModelAndView testCacheGet() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data");
		mav.addObject("data", cacheManager.getCache().forSubject("BTC"));
		return mav;
	}
	
	class CoinData {
		private float value;

		//	...	other data here ... //
		
		public CoinData(float value) {
			this.setValue(value);
		}

		public float getValue() {
			return value;
		}

		public void setValue(float value) {
			this.value = value;
		}
				
		@Override
		public String toString() {
			String ret = "";
			
			ret += "value=" + value;
			
			return ret;
		}
	}

}
