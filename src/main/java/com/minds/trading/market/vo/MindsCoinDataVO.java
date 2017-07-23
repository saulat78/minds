package com.minds.trading.market.vo;

import com.google.gson.Gson;

import java.io.Serializable;
import java.math.BigDecimal;


public class MindsCoinDataVO implements Serializable
{

	private static final long serialVersionUID = -4738873402157465148L;
	public final BigDecimal last;
    public final BigDecimal lowestAsk;
    public final BigDecimal highestBid;
    public final BigDecimal percentChange;
    public final BigDecimal baseVolume;
    public final BigDecimal quoteVolume;

    public MindsCoinDataVO(BigDecimal last, BigDecimal lowestAsk, BigDecimal highestBid, BigDecimal percentChange, BigDecimal baseVolume, BigDecimal quoteVolume)
    {
        this.last = last;
        this.lowestAsk = lowestAsk;
        this.highestBid = highestBid;
        this.percentChange = percentChange;
        this.baseVolume = baseVolume;
        this.quoteVolume = quoteVolume;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }

	public BigDecimal getLast() {
		return last;
	}
    
    
}
