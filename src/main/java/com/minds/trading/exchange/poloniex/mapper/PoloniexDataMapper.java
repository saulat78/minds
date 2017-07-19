package com.minds.trading.exchange.poloniex.mapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.minds.trading.exchange.model.poloniex.PoloniexChartData;
import com.minds.trading.exchange.model.poloniex.PoloniexCompleteBalance;
import com.minds.trading.exchange.model.poloniex.PoloniexFeeInfo;
import com.minds.trading.exchange.model.poloniex.PoloniexOpenOrder;
import com.minds.trading.exchange.model.poloniex.PoloniexOrderResult;
import com.minds.trading.exchange.model.poloniex.PoloniexTicker;
import com.minds.trading.exchange.model.poloniex.PoloniexTradeHistory;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class PoloniexDataMapper
{
    private final Gson gson;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PoloniexDataMapper()
    {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>()
        {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
            {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DTF);
            }
        }).create();
    }

    public List<PoloniexChartData> mapChartData(String chartDataResult)
    {
        PoloniexChartData[] chartDataResults = gson.fromJson(chartDataResult, PoloniexChartData[].class);
        return Arrays.asList(chartDataResults);
    }

    public PoloniexFeeInfo mapFeeInfo(String feeInfoResult)
    {
        PoloniexFeeInfo feeInfo = gson.fromJson(feeInfoResult, new TypeToken<PoloniexFeeInfo>()
        {
        }.getType());

        return feeInfo;
    }

  

    public PoloniexTicker mapTickerForCurrency(String currencyType, String tickerData)
    {
        Map<String, PoloniexTicker> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, PoloniexTicker>>()
        {
        }.getType());
        return tickerResults.get(currencyType);
    }

    public List<String> mapMarkets(String tickerData)
    {
        Map<String, PoloniexTicker> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, PoloniexTicker>>()
        {
        }.getType());
        return new ArrayList<>(tickerResults.keySet());
    }

    public PoloniexCompleteBalance mapCompleteBalanceResultForCurrency(String currencyType, String completeBalanceResults)
    {
        Map<String, PoloniexCompleteBalance> balanceResults = gson.fromJson(completeBalanceResults, new TypeToken<Map<String, PoloniexCompleteBalance>>()
        {
        }.getType());
        return balanceResults.get(currencyType);
    }

    public List<PoloniexOpenOrder> mapOpenOrders(String openOrdersResults)
    {
        List<PoloniexOpenOrder> openOrders = gson.fromJson(openOrdersResults, new TypeToken<List<PoloniexOpenOrder>>()
        {
        }.getType());
        return openOrders;
    }

    public List<PoloniexTradeHistory> mapTradeHistory(String tradeHistoryResults)
    {
        List<PoloniexTradeHistory> tradeHistory = gson.fromJson(tradeHistoryResults, new TypeToken<List<PoloniexTradeHistory>>()
        {
        }.getType());
        return tradeHistory;
    }

    public boolean mapCancelOrder(String cancelOrderResult)
    {
        int success = gson.fromJson(cancelOrderResult, JsonObject.class).get("success").getAsInt();
        return success == 1;
    }

    public PoloniexOrderResult mapTradeOrder(String orderResult)
    {
        PoloniexOrderResult tradeOrderResult = gson.fromJson(orderResult, new TypeToken<PoloniexOrderResult>()
        {
        }.getType());
        return tradeOrderResult;
    }

   

}
