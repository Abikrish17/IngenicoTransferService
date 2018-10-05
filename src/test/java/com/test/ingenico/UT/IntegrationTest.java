package com.test.ingenico.UT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.test.ingenico.domain.Account;
import com.test.ingenico.domain.AccountRepository;
import com.test.ingenico.model.AccRequest;
import com.test.ingenico.model.Transfer;

public class IntegrationTest extends AbstractTest{
	@Autowired
	AccountRepository acc;
	@Override
	   @Before
	   public void setUp() {
	      super.setUp();
	      //Create 2 accounts initially
	      
	      
	      
	   }
	
	@Test
	public void createAccount() throws Exception {
	   String uri = "/v1/bank/createAccount";
	   AccRequest req=new AccRequest();
		req.setAmount("100.0");
		req.setName("AccName");
	   
	   String inputJson = super.mapToJson(req);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   assertNotNull(content);
	}
	
	
	@Test
	public void transfer() throws Exception {
		
		Account acc1=new Account();
	      acc1.setAccNum(new Long(1));
acc1.setBalance(new BigDecimal(100.0));
acc1.setName("Account1");
acc1.setVersion(new Long(0));

		
	      Account acc2=new Account();
	      acc2.setAccNum(new Long(2));
	      acc2.setBalance(new BigDecimal(200.0));
	      acc2.setName("Account2");
	      acc2.setVersion(new Long(0));
	      acc.save(acc1);
	      acc.save(acc2);
	   String uri = "/v1/bank/transfer";
	   Transfer req=new Transfer();
		req.setDeposit("10.0");
		req.setFromAccNum(new Long(2));
		req.setToAccNum(new Long(1));
	   
	   String inputJson = super.mapToJson(req);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(content, "Transfer Success");
	}

}
