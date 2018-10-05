package com.test.ingenico.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errorMsg",
    "status",
})
public class TransferSvcException extends Exception{
	@JsonProperty("errorMsg") 
	private String errorMsg=null;
	@JsonProperty("status") 
	private String status=null;
	public TransferSvcException(String errorMsg,String status)
	{
		this.errorMsg=errorMsg;
		this.status=status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
