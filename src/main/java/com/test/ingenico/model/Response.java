package com.test.ingenico.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"status",
	"code"
})
public class Response {
	@JsonProperty("status")
private String status=null;
	@JsonProperty("code")
private String code=null;
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}

}
