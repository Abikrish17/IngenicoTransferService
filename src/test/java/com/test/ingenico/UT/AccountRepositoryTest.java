package com.test.ingenico.UT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.ingenico.TransferServiceApplication;
import com.test.ingenico.domain.Account;
import com.test.ingenico.domain.AccountRepository;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {
	@Autowired
	AccountRepository accountRepository;
	
	@Test
	public void testCreateAccount()
	{
		Long accNum=new Long(3);
		Account acc=new Account();
		acc.setAccNum(accNum);
		acc.setBalance(new BigDecimal(100.0));
		acc.setName("Name");
				Account account=accountRepository.save(acc);
		assertNotNull(account.getAccNum());
	}
	
	@Test
	public void findByIdTest()
	{
		Long accNum=new Long(4);
		Account acc=new Account();
		acc.setAccNum(accNum);
		Account Account=accountRepository.save(acc);
		Optional<Account> account=accountRepository.findById(Account.getAccNum());
		assertNotNull(account.get().getAccNum());
	}
	
	@Test
	public void transferAmtTest()
	{
		Long accNum=new Long(3);
		Account acc=new Account();
		acc.setAccNum(accNum);
		acc.setBalance(new BigDecimal(100.0));
		acc.setName("Name");
		Account account=accountRepository.save(acc);
		int result=accountRepository.transferAmount(account.getBalance(),account.getAccNum());
		assertEquals(1,result);
	}


}
