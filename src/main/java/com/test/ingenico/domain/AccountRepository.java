package com.test.ingenico.domain;

/*
 * @Author Abinaya R
 * Created AccountRepository.java
 */
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
/*
 * AccountRepository.java extends Spring JPA's JpaRepository interface to use its methods.
 * @Repository This tells Spring to bootstrap the repository during component scan.
 * @Transactional to perform database transactions
 * Domain class defining methods for performing data base operations.
 */
@Repository
@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class) 
public interface AccountRepository extends JpaRepository<Account,Long>{
	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(S)
	 * 
	 * create  new account with details (@accNum,@name,@amount) in data base.
	 * @param Account holding name and initial amount and autogenerated Account Number
	 * @return Account consisting of newly create account details - account Number,name,amount
	 */
	Account save(Account acc);
	
	
	/*Method to perform update operation
	 * Transfer amount from one account to another account
	 * Debit from first account and credit to second account
	 * @param balance to update
	 * @param accountNumber to update the balance
	 * @return 1 if update is successful
	 *         0 if no update has happened/no rows affected.
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class) 
	@Modifying
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.accNum = :accNum")
	public int transferAmount(@Param("balance") BigDecimal balance,@Param("accNum") Long accNum);
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findById(java.lang.Object)
	 * 
	 * check if provided accountNumber is present in database
	 * Query details for the accountNumber provided
	 * @param accountNumber
	 * @return Account details of the account number provided
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class) 
	public Optional<Account> findById(Long accNum);
	

}