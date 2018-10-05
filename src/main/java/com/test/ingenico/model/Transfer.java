package com.test.ingenico.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "fromAccNum",
    "ToAccNum",
    "deposit"
})
public class Transfer {
	@JsonProperty("fromAccNum") 
	Long fromAccNum=null;
	@JsonProperty("toAccNum") 
	Long toAccNum=null;
	@JsonProperty("deposit") 
	String deposit=null;
	
	public Long getFromAccNum() {
		return fromAccNum;
	}
	public void setFromAccNum(Long fromAccNum) {
		this.fromAccNum = fromAccNum;
	}
	public Long getToAccNum() {
		return toAccNum;
	}
	public void setToAccNum(Long toAccNum) {
		this.toAccNum = toAccNum;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	

}

