package com.test.ingenico.controller;
/*
 * @Author Abinaya R
 * Created TransferController.java
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.ingenico.model.AccRequest;
import com.test.ingenico.model.Transfer;
import com.test.ingenico.model.TransferSvcException;
import com.test.ingenico.service.TransferService;
import com.test.ingenico.util.ValidationUtil;

/*
 * TransferController.java
 * @RestController annotation. This tells Spring to bootstrap the controller during component scan.
 * This class acts as controller holding endpoints for TransferService API
 * It handles the incoming Http request with /v1/bank path.
 * Invokes Http Methods based on the URI provided
 *
 */
@RestController
@RequestMapping("v1/bank")
public class TransferController {
	
	@Autowired
	TransferService transferService;
	
	@Autowired 
	ValidationUtil validation;
	
	/*
	 * Maps http request with v1/bank/createAccount path
	 * Http POST method to createAccount based on Input Passed and returns AccountNumber.
	 * Validates the incoming request parameters - check if Account Holder Name(@ name) and amount(@amount) is provided.
	 * @return  AccountNumber created for the account name passed and Balance.
	 * @param AccRequest consisting of Account Holder Name and Initial Balance for opening the account.
	 * @throws TransferSvcException for Input validation failure/Failed to create Account Number
	 */
	@PostMapping("/createAccount")
	public ResponseEntity createAccount(@RequestBody AccRequest req)throws TransferSvcException
	{
	
		if(req==null)
			throw new TransferSvcException("Request cannot be null","400");
		
		try {
			//Validate Request
			validation.validateAccName(req.getName()); 
			validation.validateBalanceIsNum(req.getAmount());
			validation.validateBalance(req.getAmount());
			//create Account
			String accountNumber=transferService.createAccount(req);
			//check if account Number created or not and populate message accordingly.
			accountNumber=checkNullEmpty(accountNumber)?"Hello "+ " "+req.getName()+","+" "+"Account Number"+" "+accountNumber+" "+"created successfully.":
				"Hello "+ " "+req.getName()+","+" "+"Account Number creation failed.";
			//return Account Number Created to client AND message = ACCOUNT CREATED SUCCESSFULLY or FAILED.
			return new ResponseEntity<String>(accountNumber,HttpStatus.OK);
		} catch (TransferSvcException e) {
			return new ResponseEntity<String>(e.getErrorMsg(),HttpStatus.BAD_REQUEST);
		}
		
	}
	/*
	 * Maps http request with v1/bank/transfer path
	 * Http POST method to transferAmt based on Input Passed and returns status of transfer.
	 * Validates the incoming request parameters - check if Account Number(@fromAcc) 
	 * and amount(@amount) of debit account,Account Number(@toAcc) of credit account is provided.
	 * 
	 * @return  status of account transfer
	 * @param AccRequest consisting of Account Number , amount for transfer from account to be debited 
	 * 			and account Number of account to credit the amount.
	 * @throws TransferSvcException for Input validation failure/Failed to Transfer Amount
	 */
	
	@PostMapping("/transfer")
	public ResponseEntity transferAmt(@RequestBody Transfer req) throws Exception
	{
		//initial counter for no of retry attempts
		int counter=0;
		String status=null;
		try
		{
			//Validate Request
			validation.validateAccNum(req.getFromAccNum());
			validation.validateAccNum(req.getToAccNum());
			validation.validateBalanceIsNum(req.getDeposit());
			validation.validateBalance(req.getDeposit());
			validation.validateFromToAccNum(req.getFromAccNum(),req.getToAccNum());
			//Initiate Transfer
			status=transferService.checkAccBalanceAndTransfer(req);
			//return status of transfer
			return new ResponseEntity<String>(status,HttpStatus.OK);
		}catch(TransferSvcException e)
		{
			return new ResponseEntity<String>(e.getErrorMsg(),HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			//If DB transaction fails for concurrent requests , initiate transfer again
			if(e instanceof ObjectOptimisticLockingFailureException)
			{
				counter++;
				if(counter<=2)// Limit retry if count is >= 2.
				{	
			status=transferService.checkAccBalanceAndTransfer(req);
			return new ResponseEntity<String>(status,HttpStatus.OK);
				}else return new ResponseEntity<String>("Retry limit exceeded",HttpStatus.OK);
			}
			else
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/*
	 * Utility method Check if string is null /empty
	 * 
	 */
	private boolean checkNullEmpty(String str)
	{
		if(!"".equalsIgnoreCase(str)&&null!=str)
			return true;
		else
			return false;
	}

}
