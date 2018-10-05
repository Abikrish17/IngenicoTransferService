package com.test.ingenico.domain;

/*
 * @Author Abinaya R
 * Created Account.java
 */

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Version;


/*
 * Entity class holding DB objects.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account {
		@javax.persistence.Id
		 @GeneratedValue//(strategy = GenerationType.IDENTITY)
		//@Column(name="ACCNUM")
		private Long accNum;
		//@Column(name="NAME")
		private String name;
		//@Column(name="BALANCE")
		private BigDecimal balance;
		@Version
	    private Long version;
		
		
		public Long getVersion() {
			return version;
		}
		public void setVersion(Long version) {
			this.version = version;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getAccNum() {
			return accNum;
		}
		public void setAccNum(Long accNum) {
			this.accNum = accNum;
		}
		public BigDecimal getBalance() {
			return balance;
		}
		public void setBalance(BigDecimal balance) {
			this.balance = balance;
		}
		
		@Override
	    public String toString() {
	        return "Account{" +
	                "accNum=" + accNum+
	                ", name='" + name + '\'' +
	                ", balance=" + balance.toString() +
	                '}';
	    }

}
