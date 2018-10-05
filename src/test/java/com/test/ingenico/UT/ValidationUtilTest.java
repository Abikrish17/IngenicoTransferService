package com.test.ingenico.UT;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.ingenico.model.TransferSvcException;
import com.test.ingenico.util.ValidationUtil;

public class ValidationUtilTest {
	
	@Autowired
	ValidationUtil validationUtil=new ValidationUtil();
	
	@Test(expected = TransferSvcException.class)
	public void testNullBalance()throws TransferSvcException
	{
		String balance=null;
		validationUtil.validateBalance(balance);
	}
	
	@Test(expected = TransferSvcException.class)
	public void testNegativeBalance() throws TransferSvcException
	{
		String balance="-10.0";
		validationUtil.validateBalance(balance);
	}
	@Test(expected = TransferSvcException.class)
	public void testZeroBalance() throws TransferSvcException
	{
		String balance="0";
		validationUtil.validateBalance(balance);
	}
	
	@Test(expected = TransferSvcException.class)
	public void testNonNumberBalance() throws TransferSvcException
	{
		validationUtil.validateBalanceIsNum("str");
	}
	
	@Test(expected = TransferSvcException.class)
	public void testNullName() throws TransferSvcException
	{
		validationUtil.validateAccName(null);
	}
	

	@Test
	public void testValidateAccNum() throws TransferSvcException
	{
		validationUtil.validateAccNum(new Long(0));
	}
	

	@Test(expected = TransferSvcException.class)
	public void testValidateFromToAccNum()throws TransferSvcException
	{
		validationUtil.validateFromToAccNum(new Long(0), new Long(0));
	}
	
	

}
