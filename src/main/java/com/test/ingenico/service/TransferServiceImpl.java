package com.test.ingenico.service;
/*
 * @Author Abinaya R
 * Created TransferServiceImpl.java
 */
import java.math.BigDecimal;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.test.ingenico.domain.Account;
import com.test.ingenico.domain.AccountRepository;
import com.test.ingenico.model.AccRequest;
import com.test.ingenico.model.Transfer;
import com.test.ingenico.model.TransferSvcException;
import com.test.ingenico.util.TransferConstants;
/*
 * TransferServiceImpl.java extends TransferService Interface
 * @Service  This tells Spring to bootstrap the service during component scan.
 * 
 *  method to create new Account with name and amount passed in input
 *  method to initiate transfer of funds from one account to other account specified in input
 *  
 */
@Service
public class TransferServiceImpl
implements TransferService{

	@Autowired
	com.test.ingenico.domain.AccountRepository accountRepository;

	public TransferServiceImpl(AccountRepository accountRepository1)
	{
		this.accountRepository=accountRepository1;
	}
	/*
	 * (non-Javadoc)
	 * @see com.test.ingenico.service.TransferService#createAccount(com.test.ingenico.model.AccRequest)
	 * 
	 * Method to create New Account - Invokes data layer to create account in data base.
	 * Maps input request(AccRequest.java) to domain object(Account.java)
	 * @param AccRequest
	 * @return Account Number if Account is created
	 * 			Failure result if account  creation fails
	 */
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class) 
	public String createAccount(AccRequest req) {
		Account acc=new Account();
		//Get Input Values and map to Account 
		if(req!=null)
		{
			BigDecimal bd=new BigDecimal(req.getAmount());
			acc.setBalance(bd);
			acc.setName(req.getName());
		}
		//Invoke DB to create Account by passing values:
		if(acc!=null)
			acc=accountRepository.save(acc);//create Account
		Long accNum=acc!=null&&acc.getAccNum()!=null?acc.getAccNum():0;
		String accNumber=accNum!=0?accNum.toString():TransferConstants.ERRORCREATEACCOUNT;
		return accNumber;
	}
	/*
	 * (non-Javadoc)
	 * @see com.test.ingenico.service.TransferService#checkAccBalanceAndTransfer(com.test.ingenico.model.Transfer)
	 * 
	 * Method to initiate transfer from one account to other
	 * Map input request(Transfer.java) to Domain object(Account.java)
	 * Check if debit account number is present in data base
	 * Check if credit account number is present in data base
	 * check if debit amount is not more than the balance of debit account 
	 * 			-throw Exception if Account balance is insuficient to transfer
	 * Initiate transfer from debit account to credit account
	 * @param Transfer - details of from and to account
	 * @returns status of account Transfer
	 * @TransferSvcException if account transfer fails
	 * 
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
	public  String checkAccBalanceAndTransfer(Transfer req) throws TransferSvcException {

		//Initializing local variables
		BigDecimal deposit=null;
		Long fromAccNumber=null;
		Long toAccNumber=null;
		String status=null;

			//inflate from values
			fromAccNumber=req.getFromAccNum();
			//Amount to be deposited 
			deposit=new BigDecimal(req.getDeposit());
			
			//inflate to values
			toAccNumber=req.getToAccNum();

			//check if account number is present in DB.
			//get Debit account balance
			Account debitAcc=new Account();
			Account creditAcc=new Account();
			
			 debitAcc=getAccount(fromAccNumber);
			
			//get Credit account balance
			 creditAcc=getAccount(toAccNumber);
			
			

			//check if sufficient balance available
			if(debitAcc.getBalance().compareTo(deposit) < 0)
				throw new TransferSvcException(TransferConstants.INSUFFICIENT_BALANCE,TransferConstants.HTTP_BADREQUEST);

				//Initiate Transfer - withdraw amount from debit acco
			
			BigDecimal transferAMt=debitAcc.getBalance().subtract(deposit);
			debitAcc.setBalance(transferAMt);
			debitAcc=accountRepository.save(debitAcc);
			
			BigDecimal transferAmt2=creditAcc.getBalance().add(deposit);
			creditAcc.setBalance(transferAmt2);
			creditAcc=accountRepository.save(creditAcc);
			boolean transferStatus=debitAcc.getBalance().equals(transferAMt)&&creditAcc.getBalance().equals(transferAmt2)?true:false;
			if(transferStatus)status=TransferConstants.SUCCESS;
			else status=TransferConstants.FAILURE;
			
			
			//uncomment - to test simultaneous requests
			/*try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
				

		return status;
	}
	
	
	
	
	
	
	/*
	
	/*
	 * Method to transfer amount and update in DB.
	 * 
	 * @param amount to be either withdrawn or credit for corresponding accNumber
	 * @returns int 1 if amount is debited successfully - DB is updated
	 * 				0 if DB update fails. - no rows are affected/updated.
	 * 
	 */
	
	public  int transfer(BigDecimal amount,Long accNumber) {
		//transfer from one account to other
		return accountRepository.transferAmount(amount, accNumber);
	
	}
	

	/*
	 * Method to check if Account Number is present
	 * If Account Number is present, retrieve corresponding account details.
	 * @TransferSvcException if Account Number is not present in DB.
	 * @param accNumber
	 * @returns accountDeatils for the acc Number passed.
	 * 
	 */
	
	public  synchronized Account getAccount(Long accNumber) throws TransferSvcException {
	Account accountInfo=accountRepository.findById(accNumber).orElseThrow(() -> new TransferSvcException("Account Number Not Found","400"));
		return accountInfo;
	}

}
