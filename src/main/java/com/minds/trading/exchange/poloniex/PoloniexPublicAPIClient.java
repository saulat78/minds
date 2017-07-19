package com.minds.trading.exchange.poloniex;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minds.trading.exchange.PriceDataAPIClient;
import com.minds.trading.exchange.client.HTTPClient;


public class PoloniexPublicAPIClient implements PriceDataAPIClient
{

    private static final String PUBLIC_URL = "https://poloniex.com/public?";
    private final HTTPClient client;
    private static Logger log = LoggerFactory.getLogger(PoloniexPublicAPIClient.class);

    public PoloniexPublicAPIClient()
    {
        this.client = new HTTPClient();
    }

    public PoloniexPublicAPIClient(HTTPClient client)
    {
        this.client = client;
    }

    @Override
    public String returnTicker()
    {
        try
        {
            String url = PUBLIC_URL + "command=returnTicker";
            return client.getHttp(url, null);
        }
        catch (IOException ex)
        {
            log.error("Call to return ticker API resulted in exception - " + ex.getMessage(), ex);
        }

        return null;
    }

    @Override
    public String getUSDBTCChartData(Long periodInSeconds, Long startEpochInSeconds)
    {
        String currencyPair = "USDT_BTC";
        return getChartData(currencyPair, periodInSeconds, startEpochInSeconds);
    }

    @Override
    public String getUSDETHChartData(Long periodInSeconds, Long startEpochInSeconds)
    {
        String currencyPair = "USDT_ETH";
        return getChartData(currencyPair, periodInSeconds, startEpochInSeconds);
    }

    @Override
    public String getChartData(String currencyPair, Long periodInSeconds, Long startEpochSeconds)
    {
        return getChartData(currencyPair, periodInSeconds, startEpochSeconds, 9999999999L);
    }

    @Override
    public String getChartData(String currencyPair, Long periodInSeconds, Long startEpochSeconds, Long endEpochSeconds)
    {
        return getChartData(currencyPair, startEpochSeconds.toString(), endEpochSeconds.toString(), periodInSeconds.toString());
    }

    private String getChartData(String currencyPair, String startEpochInSec, String endEpochInSec, String periodInSec)
    {
        try
        {
            String url = PUBLIC_URL + "command=returnChartData&currencyPair=" + currencyPair + "&start=" + startEpochInSec + "&end=" + endEpochInSec + "&period=" + periodInSec;
            return client.getHttp(url, null);
        }
        catch (IOException ex)
        {
           log.error("Call to Chart Data API resulted in exception - " + ex.getMessage(), ex);
        }

        return null;
    }

}
