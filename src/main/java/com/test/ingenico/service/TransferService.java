package com.test.ingenico.service;
/*
 * @Author Abinaya R
 * Created TransferService.java
 */

import com.test.ingenico.model.AccRequest;
import com.test.ingenico.model.Transfer;
/*
 * TransferService.java
 * Interface holding method declarations for TransferServiceImpl.java
 */
public interface TransferService {
	
	public String createAccount(AccRequest req);
	
	public String checkAccBalanceAndTransfer(Transfer req) throws Exception;
	

}
