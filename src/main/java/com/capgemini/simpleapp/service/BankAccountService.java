package com.capgemini.simpleapp.service;

import com.capgemini.simpleapp.exception.AccountNotFoundException;
import com.capgemini.simpleapp.exception.InsufficientAccountBalanceException;
import com.capgemini.simpleapp.exception.NegativeAmountException;

public interface BankAccountService {
	
	public double getBalance(long accountId);
	public double withdraw(long accountId, double amount);
	public double deposit(long accountId, double amount);
	public boolean fundTransfer(long fromAcc, long toAcc, double amount) throws InsufficientAccountBalanceException,AccountNotFoundException, NegativeAmountException;


}
