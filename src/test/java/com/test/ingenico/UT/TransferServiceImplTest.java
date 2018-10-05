package com.test.ingenico.UT;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.test.ingenico.TransferServiceApplication;
import com.test.ingenico.domain.Account;
import com.test.ingenico.domain.AccountRepository;
import com.test.ingenico.model.AccRequest;
import com.test.ingenico.model.Transfer;
import com.test.ingenico.model.TransferSvcException;
import com.test.ingenico.service.TransferServiceImpl;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes =TransferServiceApplication.class)
public class TransferServiceImplTest {
	@Autowired
	TransferServiceImpl transferService;
	
	@Before
	public void setup()
	{
		AccRequest accReq=new AccRequest();
		accReq.setName("Test");
		accReq.setAmount("100.0");
		
		String accNum=transferService.createAccount(accReq);
		assertNotNull(accNum);
	}
	
	@Test
	public void testCreateAccount()
	{
		AccRequest accReq=new AccRequest();
		accReq.setName("Test");
		accReq.setAmount("100.0");
		
		String accNum=transferService.createAccount(accReq);
		assertNotNull(accNum);
	}
	
	@Test
	public void testCreateAccountFailure()
	{
		
		Account account=new Account();
		Account account1=new Account();
		account.setAccNum(new Long(3));
		account.setBalance(new BigDecimal(10.0));
		account.setName("TestAccount");
		AccountRepository mockAcc=mock(AccountRepository.class);
		when(mockAcc.save(account1)).thenReturn(account);
		TransferServiceImpl TransferServiceImpl=new TransferServiceImpl(mockAcc);
		AccRequest accReq=new AccRequest();
		accReq.setName("Test");
		accReq.setAmount("100.0");
		
		String accNum=TransferServiceImpl.createAccount(accReq);
		assertEquals("Error Creating Account ",accNum);
	}
	
	@Test
	public void testTransferAmt()
	{
		Transfer trans = new Transfer();
		trans.setDeposit("10.0");
		trans.setFromAccNum(new Long(1));
		trans.setToAccNum(new Long(2));
		
		
		try {
			String status=transferService.checkAccBalanceAndTransfer(trans);
			
			assertEquals("Transfer Success",status);
		} catch (TransferSvcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = TransferSvcException.class)
	public void testTransferAmtLimit() throws TransferSvcException
	{
		Transfer trans = new Transfer();
		trans.setDeposit("200.0");
		trans.setFromAccNum(new Long(1));
		trans.setToAccNum(new Long(2));
		
		
			String status=transferService.checkAccBalanceAndTransfer(trans);
			assertEquals("Transfer Successful",status);
		
	}
	
	
	@Test(expected = TransferSvcException.class)
	public void testTransferAmtAccNumbNotFound() throws TransferSvcException
	{
		Transfer trans = new Transfer();
		trans.setDeposit("10.0");
		trans.setFromAccNum(new Long(1));
		trans.setToAccNum(new Long(1));
		
			AccountRepository mockAcc=mock(AccountRepository.class);
			when(mockAcc.transferAmount(new BigDecimal(10.0), new Long(4))).thenReturn(0);
			TransferServiceImpl TransferServiceImpl=new TransferServiceImpl(mockAcc);
			TransferServiceImpl.checkAccBalanceAndTransfer(trans);
			
		
	}

}
