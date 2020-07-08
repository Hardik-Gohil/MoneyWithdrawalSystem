package com.globalvoxinc.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Digits;

public class BankAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bankAccountNumber;
	@Digits(integer = 15, fraction = 2)
	private double monthlySalary;
	private double initialMonthlyWithdrawalLimit;
	private double monthlyWithdrawalLimit;
	private double currentBalance;
	private List<TransactionHistory> transactionHistoryList;

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public double getMonthlySalary() {
		return monthlySalary;
	}

	public void setMonthlySalary(double monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	public double getInitialMonthlyWithdrawalLimit() {
		return initialMonthlyWithdrawalLimit;
	}

	public void setInitialMonthlyWithdrawalLimit(double initialMonthlyWithdrawalLimit) {
		this.initialMonthlyWithdrawalLimit = initialMonthlyWithdrawalLimit;
	}

	public double getMonthlyWithdrawalLimit() {
		return monthlyWithdrawalLimit;
	}

	public void setMonthlyWithdrawalLimit(double monthlyWithdrawalLimit) {
		this.monthlyWithdrawalLimit = monthlyWithdrawalLimit;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public List<TransactionHistory> getTransactionHistoryList() {
		return transactionHistoryList;
	}

	public void setTransactionHistoryList(List<TransactionHistory> transactionHistoryList) {
		this.transactionHistoryList = transactionHistoryList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankAccountNumber == null) ? 0 : bankAccountNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (bankAccountNumber == null) {
			if (other.bankAccountNumber != null)
				return false;
		} else if (!bankAccountNumber.equals(other.bankAccountNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BankAccount [bankAccountNumber=" + bankAccountNumber + ", monthlySalary=" + monthlySalary
				+ ", initialMonthlyWithdrawalLimit=" + initialMonthlyWithdrawalLimit + ", monthlyWithdrawalLimit="
				+ monthlyWithdrawalLimit + ", currentBalance=" + currentBalance + ", transactionHistoryList="
				+ transactionHistoryList + "]";
	}
}
