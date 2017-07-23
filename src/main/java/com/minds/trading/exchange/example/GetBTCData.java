package com.minds.trading.exchange.example;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.exchange.MindsExchangeService;
import com.minds.trading.exchange.poloniex.PoloniexExchangeService;
import com.minds.trading.market.vo.MindsChartDataVO;



public class GetBTCData
{
    private final static Logger log = LoggerFactory.getLogger(GetBTCData.class);
    private final static String DEFAULT_PROPERTIES_FILE = "local.properties";
    private final static String POLONIEX_API_KEY_PROP_NAME = "poloniex.api.key";
    private final static String POLONIEX_API_SECRET_PROP_NAME = "poloniex.api.secret";

    public static void main(String[] args)
    {
    	
        String propertiesFileName = args.length > 0 ? args[0] : DEFAULT_PROPERTIES_FILE;
        new GetBTCData().run(propertiesFileName);
    }

    public void run(String propertiesFileName)
    {
    
        Properties properties = this.loadProperties(propertiesFileName);

        String tradingAPIKey = properties.getProperty(POLONIEX_API_KEY_PROP_NAME);
        if (tradingAPIKey == null)
        {
            log.warn("Did not find value for " + POLONIEX_API_KEY_PROP_NAME + " in " + propertiesFileName + ". Trading API commands will fail");
        }

        String tradingAPISecret = properties.getProperty(POLONIEX_API_SECRET_PROP_NAME);
        if (tradingAPISecret == null)
        {
            log.warn("Did not find value for " + POLONIEX_API_SECRET_PROP_NAME + " in " + propertiesFileName + ". Trading API commands will fail");
        }

        MindsExchangeService service = new PoloniexExchangeService(tradingAPIKey, tradingAPISecret);
        Long yesterdayEpochSecond = ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toEpochSecond();
        /*List<MindsChartDataVO> btcDailyChartData = service.returnChartData(PoloniexExchangeService.USDT_BTC_CURRENCY_PAIR, PoloniexExchangeService.DAILY_TIME_PERIOD, yesterdayEpochSecond);
        log.info(btcDailyChartData.toString());
        log.info(service.returnFeeInfo().toString());
        log.info(service.returnOpenOrders(MindsExchangeService.DGB_BTC_CURRENCY_PAIR).toString());
        */
        log.info("price:" + service.returnTicker(PoloniexExchangeService.USDT_BTC_CURRENCY_PAIR));
    }

    private Properties loadProperties(String propertiesFileName)
    {
        Properties properties = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName))
        {
            properties.load(in);
        }
        catch (IOException ex)
        {
            log.error("Could not load properties file " + propertiesFileName + " - " + ex.getMessage());
        }

        return properties;
    }
}

