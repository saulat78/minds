package com.minds.trading.exchange;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.minds.trading.exchange.poloniex.PoloniexExchangeService;

public class ExchangeFactory {

	 private final static Logger log = LoggerFactory.getLogger(ExchangeFactory.class);
		
	public final static int POLONIEX_SERVICE = 1;
	public final static int LOCAL_TEST_SERVICE = 0;
	private final static String DEFAULT_PROPERTIES_FILE = "exchange.properties";
	private final static String POLONIEX_API_KEY_PROP_NAME = "poloniex.api.key";
	private final static String POLONIEX_API_SECRET_PROP_NAME = "poloniex.api.secret";

	public static MindsExchangeService getExchangeService(int type) {
		if (type == POLONIEX_SERVICE)
			return buildPoloniexService("local.properties");
		return null;
	}

	private static MindsExchangeService buildPoloniexService(String propertiesFileName) 
	{

		Properties properties = loadProperties(propertiesFileName);
		String tradingAPIKey = properties.getProperty(POLONIEX_API_KEY_PROP_NAME);
		if (tradingAPIKey == null) {
			log.warn("Did not find value for " + POLONIEX_API_KEY_PROP_NAME + " in " + propertiesFileName
					+ ". Trading API commands will fail");
		}

		String tradingAPISecret = properties.getProperty(POLONIEX_API_SECRET_PROP_NAME);
		if (tradingAPISecret == null) {
			log.warn("Did not find value for " + POLONIEX_API_SECRET_PROP_NAME + " in " + propertiesFileName
					+ ". Trading API commands will fail");
		}
		return new PoloniexExchangeService(tradingAPIKey, tradingAPISecret);
	}

	private static Properties loadProperties(String propertiesFileName) {
		Properties properties = new Properties();
		try (InputStream in = ExchangeFactory.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
			properties.load(in);
		} catch (IOException ex) {
			log.error("Could not load properties file " + propertiesFileName + " - " + ex.getMessage());
		}

		return properties;
	}

}
