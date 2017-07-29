package com.minds.trading.bot.tradingbot.wallet;

import java.text.DecimalFormat;

public class WalletEntry
{
	private DecimalFormat df2 = new DecimalFormat("#.########");
	private String currencyPair;
	private int quantity;
	private double totalPrice;
	
	public WalletEntry(String currencyPair, int quantity, double totalPrice) {
		super();
		this.currencyPair = currencyPair;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	public String getCurrencyPair() {
		return currencyPair;
	}
	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(totalPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currencyPair == null) ? 0 : currencyPair.hashCode());
		result = prime * result + quantity;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WalletEntry other = (WalletEntry) obj;
		if (Double.doubleToLongBits(totalPrice) != Double.doubleToLongBits(other.totalPrice))
			return false;
		if (currencyPair == null) {
			if (other.currencyPair != null)
				return false;
		} else if (!currencyPair.equals(other.currencyPair))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
	@Override
	public String toString() {
		double avgPrice = getTotalPrice()/getQuantity();
		return "WalletEntry [currencyPair=" + currencyPair + ", quantity=" + quantity + ", totalPrice=" + df2.format(totalPrice)
				+ " avgPrice="+  df2.format(avgPrice)+"]";
	}
	
	
	
}
