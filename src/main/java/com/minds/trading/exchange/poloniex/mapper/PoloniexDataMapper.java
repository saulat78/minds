package com.minds.trading.exchange.poloniex.mapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.minds.trading.market.vo.MindsChartDataVO;
import com.minds.trading.market.vo.MindsCoinDataVO;
import com.minds.trading.market.vo.MindsCompleteBalanceVO;
import com.minds.trading.market.vo.MindsFeeInfoVO;
import com.minds.trading.market.vo.MindsOpenOrderVO;
import com.minds.trading.market.vo.MindsOrderResultVO;
import com.minds.trading.market.vo.MindsTradeHistoryVO;

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

    public List<MindsChartDataVO> mapChartData(String chartDataResult)
    {
        MindsChartDataVO[] chartDataResults = gson.fromJson(chartDataResult, MindsChartDataVO[].class);
        return Arrays.asList(chartDataResults);
    }

    public MindsFeeInfoVO mapFeeInfo(String feeInfoResult)
    {
        MindsFeeInfoVO feeInfo = gson.fromJson(feeInfoResult, new TypeToken<MindsFeeInfoVO>()
        {
        }.getType());

        return feeInfo;
    }

  

    public MindsCoinDataVO mapTickerForCurrency(String currencyType, String tickerData)
    {
        Map<String, MindsCoinDataVO> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, MindsCoinDataVO>>()
        {
        }.getType());
        return tickerResults.get(currencyType);
    }

    public List<String> mapMarkets(String tickerData)
    {
        Map<String, MindsCoinDataVO> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, MindsCoinDataVO>>()
        {
        }.getType());
        return new ArrayList<>(tickerResults.keySet());
    }

    public MindsCompleteBalanceVO mapCompleteBalanceResultForCurrency(String currencyType, String completeBalanceResults)
    {
        Map<String, MindsCompleteBalanceVO> balanceResults = gson.fromJson(completeBalanceResults, new TypeToken<Map<String, MindsCompleteBalanceVO>>()
        {
        }.getType());
        return balanceResults.get(currencyType);
    }

    public List<MindsOpenOrderVO> mapOpenOrders(String openOrdersResults)
    {
        List<MindsOpenOrderVO> openOrders = gson.fromJson(openOrdersResults, new TypeToken<List<MindsOpenOrderVO>>()
        {
        }.getType());
        return openOrders;
    }

    public List<MindsTradeHistoryVO> mapTradeHistory(String tradeHistoryResults)
    {
        List<MindsTradeHistoryVO> tradeHistory = gson.fromJson(tradeHistoryResults, new TypeToken<List<MindsTradeHistoryVO>>()
        {
        }.getType());
        return tradeHistory;
    }

    public boolean mapCancelOrder(String cancelOrderResult)
    {
        int success = gson.fromJson(cancelOrderResult, JsonObject.class).get("success").getAsInt();
        return success == 1;
    }

    public MindsOrderResultVO mapTradeOrder(String orderResult)
    {
        MindsOrderResultVO tradeOrderResult = gson.fromJson(orderResult, new TypeToken<MindsOrderResultVO>()
        {
        }.getType());
        return tradeOrderResult;
    }

   

}
