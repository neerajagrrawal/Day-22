package com.capgemini.simpleapp.entities;

import org.springframework.stereotype.Component;


public class BankAccount {

	public BankAccount() {
		super();
		
	}
private long accountId ;
private String accountType ;
private double balance ;
public BankAccount(long accountId, String accountType, long balance) {
	super();
	this.accountId = accountId;
	this.accountType = accountType;
	this.balance = balance;
}
public long getAccountId() {
	return accountId;
}
public void setAccountId(long accountId) {
	this.accountId = accountId;
}
public String getAccountType() {
	return accountType;
}
public void setAccountType(String accountType) {
	this.accountType = accountType;
}
public double getBalance() {
	return balance;
}
public void setBalance(double balance) {
	this.balance = balance;
}
}