package com.globalvoxinc.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	private int transactionHistoryId;
	private String transactionType;
	private int amount;
	private double amountInINR;
	private Currency currency;
	private int notes;
	private int notesQuantity;
	private double conversionRate;
	private int withdrawalFees;
	private double foreignTransactionFees;
	private double foreignTransactionFeesInINR;
	private Date createdOn;

	public int getTransactionHistoryId() {
		return transactionHistoryId;
	}

	public void setTransactionHistoryId(int transactionHistoryId) {
		this.transactionHistoryId = transactionHistoryId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getAmountInINR() {
		return amountInINR;
	}

	public void setAmountInINR(double amountInINR) {
		this.amountInINR = amountInINR;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public int getNotes() {
		return notes;
	}

	public void setNotes(int notes) {
		this.notes = notes;
	}

	public int getNotesQuantity() {
		return notesQuantity;
	}

	public void setNotesQuantity(int notesQuantity) {
		this.notesQuantity = notesQuantity;
	}

	public double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(double conversionRate) {
		this.conversionRate = conversionRate;
	}

	public int getWithdrawalFees() {
		return withdrawalFees;
	}

	public void setWithdrawalFees(int withdrawalFees) {
		this.withdrawalFees = withdrawalFees;
	}

	public double getForeignTransactionFees() {
		return foreignTransactionFees;
	}

	public void setForeignTransactionFees(double foreignTransactionFees) {
		this.foreignTransactionFees = foreignTransactionFees;
	}

	public double getForeignTransactionFeesInINR() {
		return foreignTransactionFeesInINR;
	}

	public void setForeignTransactionFeesInINR(double foreignTransactionFeesInINR) {
		this.foreignTransactionFeesInINR = foreignTransactionFeesInINR;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + transactionHistoryId;
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
		TransactionHistory other = (TransactionHistory) obj;
		if (transactionHistoryId != other.transactionHistoryId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransactionHistory [transactionHistoryId=" + transactionHistoryId + ", transactionType="
				+ transactionType + ", amount=" + amount + ", amountInINR=" + amountInINR + ", currency=" + currency
				+ ", notes=" + notes + ", notesQuantity=" + notesQuantity + ", conversionRate=" + conversionRate
				+ ", withdrawalFees=" + withdrawalFees + ", foreignTransactionFees=" + foreignTransactionFees
				+ ", foreignTransactionFeesInINR=" + foreignTransactionFeesInINR + ", createdOn=" + createdOn + "]";
	}

}
