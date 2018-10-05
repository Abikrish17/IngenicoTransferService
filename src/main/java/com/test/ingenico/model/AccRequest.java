package com.test.ingenico.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"name",
	"balance"
})
public class AccRequest {
	/*@JsonProperty("accNum")
	private Long accNum;*/
	@JsonProperty("name")
	private String name;
	@JsonProperty("amount")
	private String amount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
