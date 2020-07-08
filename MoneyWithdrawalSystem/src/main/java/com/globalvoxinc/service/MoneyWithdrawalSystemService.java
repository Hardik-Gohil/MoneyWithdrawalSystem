package com.globalvoxinc.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.globalvoxinc.model.Currency;
import com.globalvoxinc.model.TransactionHistory;
import com.globalvoxinc.model.UserDetails;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class MoneyWithdrawalSystemService {

	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private static UserDetails userDetails;
	private static HashMap<String, Currency> currencyMap = new HashMap<>();
	private static int transactionHistoryId = 0;

	@PostConstruct
	private void currencySetUp() {
		Currency inr = new Currency();
		Currency usd = new Currency();
		Currency gbp = new Currency();
		Currency aud = new Currency();

		inr.setName("INR");
		inr.setChoiceOfNotes(Arrays.asList(50, 100, 500, 2000));
		inr.setRateInINR(1);

		usd.setName("USD");
		usd.setChoiceOfNotes(Arrays.asList(10, 50, 100, 500));

		gbp.setName("GBP");
		gbp.setChoiceOfNotes(Arrays.asList(20, 50, 500, 1000));

		aud.setName("AUD");
		aud.setChoiceOfNotes(Arrays.asList(100, 500, 1000));

		currencyMap.put("INR", inr);
		currencyMap.put("USD", usd);
		currencyMap.put("GBP", gbp);
		currencyMap.put("AUD", aud);
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public Currency getCurrency(String Key) {
		return currencyMap.get(Key);
	}

	public List<String> getCurrencyList() {
		return currencyMap.keySet().stream().collect(Collectors.toList());
	}

	public HashMap<String, Currency> getCurrencyMap() {
		return currencyMap;
	}

	public int getTransactionHistoryId() {
		return transactionHistoryId;
	}

	public UserDetails createUser(UserDetails userDetails) {
		userDetails.getBankAccount().setBankAccountNumber(UUID.randomUUID().toString());
		userDetails.getBankAccount().setInitialMonthlyWithdrawalLimit(Double.valueOf(decimalFormat.format((userDetails.getBankAccount().getMonthlySalary() / 2))));
		userDetails.getBankAccount().setMonthlyWithdrawalLimit(Double.valueOf(decimalFormat.format(userDetails.getBankAccount().getMonthlySalary() / 2)));
		userDetails.getBankAccount().setCurrentBalance(Double.valueOf(decimalFormat.format(userDetails.getBankAccount().getMonthlySalary())));
		MoneyWithdrawalSystemService.userDetails = userDetails;
		return MoneyWithdrawalSystemService.userDetails;
	}

	public boolean deleteUser() {
		userDetails = null;
		transactionHistoryId = 0;
		return true;
	}

	public void updateCurrencyRateInINR() {
		getCurrency("USD").setRateInINR(getRateInINR("USD"));
		getCurrency("GBP").setRateInINR(getRateInINR("GBP"));
		getCurrency("AUD").setRateInINR(getRateInINR("AUD"));
	}

	public double getRateInINR(String base) {
		double rateInINR = 1;
		try {
			String url_str = "https://api.exchangeratesapi.io/latest?base=" + base;
			URL url = new URL(url_str);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();
			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonobj = root.getAsJsonObject();
			rateInINR = (jsonobj.get("rates").getAsJsonObject()).get("INR").getAsDouble();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Double.valueOf(decimalFormat.format(rateInINR));
	}

	public TransactionHistory createTransaction(String transactionType, int amount, String currencyName, int notes, double conversionRate, double foreignTransactionFees, double foreignTransactionFeesInINR) {
		TransactionHistory transactionHistory = new TransactionHistory();
		try {
			transactionHistory.setTransactionHistoryId(++transactionHistoryId);
			transactionHistory.setTransactionType(transactionType);
			transactionHistory.setAmount(amount);
			transactionHistory.setAmountInINR(Double.valueOf(decimalFormat.format(amount * conversionRate)));
			transactionHistory.setCurrency(getCurrency(currencyName));
			transactionHistory.setNotes(notes);
			transactionHistory.setNotesQuantity(amount / notes);
			transactionHistory.setConversionRate(Double.valueOf(decimalFormat.format(conversionRate)));
			transactionHistory.setWithdrawalFees(transactionHistoryId == 1 ? 0 : 5);
			transactionHistory.setForeignTransactionFees(Double.valueOf(decimalFormat.format(foreignTransactionFees)));
			transactionHistory.setForeignTransactionFeesInINR(Double.valueOf(decimalFormat.format(foreignTransactionFeesInINR)));
			transactionHistory.setCreatedOn(new Date());
			if (transactionHistoryId == 1) {
				userDetails.getBankAccount().setTransactionHistoryList(new ArrayList<TransactionHistory>());
			}
			userDetails.getBankAccount().getTransactionHistoryList().add(transactionHistory);
			double totalDeduction = transactionHistory.getAmountInINR() + transactionHistory.getWithdrawalFees()+ transactionHistory.getForeignTransactionFeesInINR();
			userDetails.getBankAccount().setMonthlyWithdrawalLimit(Double.valueOf(decimalFormat.format(userDetails.getBankAccount().getMonthlyWithdrawalLimit() - totalDeduction)));
			userDetails.getBankAccount().setCurrentBalance(Double.valueOf(decimalFormat.format(userDetails.getBankAccount().getCurrentBalance() - totalDeduction)));
		} catch (Exception e) {
			--transactionHistoryId;
		}
		return transactionHistory;
	}

}
