package com.richfood.dto;

public class ConfirmPaymentRequest {
    private int amount;
    private String currency = "TWD";
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
    
    
 
}