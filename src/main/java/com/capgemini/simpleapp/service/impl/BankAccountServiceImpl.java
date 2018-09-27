package com.capgemini.simpleapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.simpleapp.exception.AccountNotFoundException;
import com.capgemini.simpleapp.exception.InsufficientAccountBalanceException;
import com.capgemini.simpleapp.exception.NegativeAmountException;
import com.capgemini.simpleapp.repository.impl.BankAccountRepositoryImpl;
import com.capgemini.simpleapp.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountRepositoryImpl bankRepository;

	@Override
	public double getBalance(long accountId) {
		return bankRepository.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount) {
		double balance = bankRepository.getBalance(accountId);
		if (balance == -1)
			return -1;
		bankRepository.updateBalance(accountId, balance - amount);
		return bankRepository.getBalance(accountId);
	}

	@Override
	public double deposit(long accountId, double amount) {
		double balance = bankRepository.getBalance(accountId);
		if (balance == -1)
			return -1;
		bankRepository.updateBalance(accountId, (balance + amount));
		return bankRepository.getBalance(accountId);
	}

	@Override
	public boolean fundTransfer(long fromAcc, long toAcc, double amount)
			throws InsufficientAccountBalanceException, AccountNotFoundException, NegativeAmountException {
		boolean found = false;
		boolean balProblem = false;
		boolean negAmount = false;
		
		if(bankRepository.getBalance(toAcc)==-1)
		{
			throw new AccountNotFoundException("The Account Id is incorrect!!");
		}
		if (bankRepository.getBalance(fromAcc) < amount) {
			throw new InsufficientAccountBalanceException("Your account balance is low!!");
		}

		if (amount < 0) {
			throw new NegativeAmountException("You have entered negative amount");
		}

		if (withdraw(fromAcc, amount) != -1) {
			if (deposit(toAcc, amount) != -1) {
				found = true;
				return true;
			}

		}
		
		return false;
	}

}