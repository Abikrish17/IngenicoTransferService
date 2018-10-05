package com.test.ingenico.util;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.test.ingenico.model.TransferSvcException;
/*
 * ValidationUtil.java
 * Class to validate incoming requests
 */
@Component
public class ValidationUtil {
	/*
	 * Method to validate the balance passed in input.
	 * Check if balance is not null/Empty
	 * Check if balance is non-negative number.
	 * @param amount
	 * @TransferSvcException if validation fails
	 */

	public void validateBalance(String bal) throws TransferSvcException
	{
		Optional<String> balance=Optional.ofNullable(bal);
		balance.orElseThrow(() -> new TransferSvcException("Account Balance can't be empty","400"));

		
			BigDecimal bd=new BigDecimal(bal);
			//validate if balance passed is negative
			if(bd.signum() == -1)
				throw  new TransferSvcException("Account Balance can't be negative","400");
			
			if(bd.compareTo(BigDecimal.ZERO)<=0)
				throw  new TransferSvcException("Account Balance should be greater than 0","400");
	}
	/*
	 * Method to validate the balance passed in input.
	 * Check if balance is non-negative number.
	 * @param amount
	 * @TransferSvcException if validation fails
	 */
	public void validateBalanceIsNum(String bal) throws TransferSvcException
	{
		try
		{
		BigDecimal bd=new BigDecimal(bal);
		}
		catch(Exception e)
		{
			if(e instanceof NumberFormatException)
			{
				throw  new TransferSvcException("Account Balance should be a valid number","400");
			}
		}

	}
/*
 * Method to validate if account number is null/empty
 * @param account Number
 *@TransferSvcException if validation fails
 */
	public void validateAccNum(Long num) throws TransferSvcException
	{
		Optional<Long> accNum=Optional.ofNullable(num);
		accNum.orElseThrow(() -> new TransferSvcException("Account Number can't be empty","400"));
	}
	/*
	 * Method to validate if account number is null/empty
	 * @param account Number
	 *@TransferSvcException if validation fails
	 */
	public void validateAccName(String name) throws TransferSvcException
	{

		Optional<String> accName=Optional.ofNullable(name);
		accName.orElseThrow(() -> new TransferSvcException("Account Name can't be empty","400"));
	}
	/*
	 * Method to validate if both debit and credit account are same.
	 * @param account Number
	 *@TransferSvcException if validation fails
	 */
	public void validateFromToAccNum(Long accNum, Long accNum2) throws TransferSvcException{
		if(accNum.compareTo(accNum2)==0)
		{
			throw  new TransferSvcException("From Account and To Account cannot be same.","400");
		}

	}

}
