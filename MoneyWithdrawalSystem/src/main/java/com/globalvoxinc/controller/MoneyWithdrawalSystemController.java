package com.globalvoxinc.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.globalvoxinc.model.TransactionHistory;
import com.globalvoxinc.model.UserDetails;
import com.globalvoxinc.service.MoneyWithdrawalSystemService;
import com.google.gson.GsonBuilder;

@Controller
public class MoneyWithdrawalSystemController {

	@Autowired
	private MoneyWithdrawalSystemService moneyWithdrawalSystemService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(ModelMap modelMap) {
		String returnPage = "Home";
		modelMap.addAttribute("userDetails", moneyWithdrawalSystemService.getUserDetails());
		return returnPage;
	}

	@RequestMapping(value = "/register-user", method = RequestMethod.GET)
	public String registerUser(ModelMap modelMap) {
		String returnPage = "RegisterUser";
		modelMap.addAttribute("userDetails", new UserDetails());
		return returnPage;
	}

	@RequestMapping(value = "/register-user", method = RequestMethod.POST)
	public String registerUser(ModelMap modelMap, @Valid @ModelAttribute("userDetails") UserDetails userDetails,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String returnPage = "redirect:/";
		if (moneyWithdrawalSystemService.getUserDetails() != null) {
			redirectAttributes.addFlashAttribute("errorMessage", "User already exists.");
		} else if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Invalid user input.");
		} else {
			userDetails = moneyWithdrawalSystemService.createUser(userDetails);
			StringBuilder accountDetails = new StringBuilder();
			accountDetails.append("Account number:" + userDetails.getBankAccount().getBankAccountNumber());
			accountDetails.append(", Balance:" + userDetails.getBankAccount().getCurrentBalance());
			redirectAttributes.addFlashAttribute("successMessage", "User registered successfully." + accountDetails.toString());
		}
		return returnPage;
	}

	@RequestMapping(value = "/delete-user", method = RequestMethod.GET)
	public String deleteUser(ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if (moneyWithdrawalSystemService.getUserDetails() != null) {
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
			moneyWithdrawalSystemService.deleteUser();
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
		}
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "/get-available-notes", method = RequestMethod.GET)
	public String getAvailableNotes(@RequestParam("base") String base) {
		StringBuilder returnHtml = new StringBuilder();
		for (int note : moneyWithdrawalSystemService.getCurrency(base).getChoiceOfNotes()) {
			returnHtml.append(" <label class=\"radio-inline\"><input type=\"radio\" value=\"" + note + "\" name=\"notes\" onclick=\"varifyField('Notes')\"> " + note + " </input></label> ");
		}
		return returnHtml.toString();
	}

	@RequestMapping(value = "/money-withdrawal/{transactionType}", method = RequestMethod.GET)
	public String moneyWithdrawal(ModelMap modelMap, @PathVariable("transactionType") String transactionType, RedirectAttributes redirectAttributes) {
		String returnPage = "MoneyWithdrawal";
		UserDetails userDetails = moneyWithdrawalSystemService.getUserDetails();
		if (userDetails == null) {
			returnPage = "redirect:/";
			redirectAttributes.addFlashAttribute("errorMessage", "Please register user.");
		} else {
			modelMap.addAttribute("userDetails", userDetails);
			modelMap.addAttribute("isFirstTransaction", (userDetails.getBankAccount().getTransactionHistoryList() == null || userDetails.getBankAccount().getTransactionHistoryList().isEmpty()));
			if (transactionType.equals("INR")) {
				modelMap.addAttribute("currencyList", Arrays.asList("INR"));
			} else {
				List<String> currencyList = moneyWithdrawalSystemService.getCurrencyList();
				currencyList.remove("INR");
				modelMap.addAttribute("currencyList", currencyList);
				moneyWithdrawalSystemService.updateCurrencyRateInINR();
			}
			modelMap.addAttribute("currencyMap", new GsonBuilder().create().toJson(moneyWithdrawalSystemService.getCurrencyMap()));
		}
		return returnPage;
	}

	@RequestMapping(value = "/money-withdrawal", method = RequestMethod.POST)
	public String moneyWithdrawal(ModelMap modelMap, @RequestParam("transactionType") String transactionType,
			@RequestParam("amount") int amount, @RequestParam("currencyName") String currencyName,
			@RequestParam("notes") int notes, @RequestParam("conversionRate") double conversionRate,
			@RequestParam("foreignTransactionFees") double foreignTransactionFees,
			@RequestParam("foreignTransactionFeesInINR") double foreignTransactionFeesInINR,
			RedirectAttributes redirectAttributes) {
		String returnPage = "redirect:/";
		UserDetails userDetails = moneyWithdrawalSystemService.getUserDetails();
		StringBuilder transactionDetails = new StringBuilder();
		double totalDeduction = (amount * conversionRate) + (moneyWithdrawalSystemService.getTransactionHistoryId() == 0 ? 0 : 5) + foreignTransactionFeesInINR;
		if (userDetails == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please register user");
		} else if (totalDeduction > userDetails.getBankAccount().getMonthlyWithdrawalLimit()) {
			transactionDetails.append("Currunt withdrawal limit:" + userDetails.getBankAccount().getMonthlyWithdrawalLimit());
			transactionDetails.append(", Attempted withdrawal amount:" + totalDeduction);
			redirectAttributes.addFlashAttribute("errorMessage", "You can not Withdraw more than the monthly withdrawal limit." + transactionDetails.toString());
		} else {
			TransactionHistory transactionHistory = moneyWithdrawalSystemService.createTransaction(transactionType, amount, currencyName, notes, conversionRate, foreignTransactionFees, foreignTransactionFeesInINR);
			transactionDetails.append("Amount:" + transactionHistory.getAmount());
			transactionDetails.append(", Notes:" + transactionHistory.getNotes());
			transactionDetails.append(", Notes Quantity:" + transactionHistory.getNotesQuantity());
			redirectAttributes.addFlashAttribute("successMessage", "Your transaction was successful." + transactionDetails.toString());
		}
		return returnPage;
	}

	@RequestMapping(value = "/check-balance", method = RequestMethod.GET)
	public String checkBalance(ModelMap modelMap, RedirectAttributes redirectAttributes) {
		String returnPage = "ViewBalance";
		UserDetails userDetails = moneyWithdrawalSystemService.getUserDetails();
		if (userDetails == null) {
			returnPage = "redirect:/";
			redirectAttributes.addFlashAttribute("errorMessage", "Please register user.");
		} else {
			modelMap.addAttribute("userDetails", moneyWithdrawalSystemService.getUserDetails());
		}
		return returnPage;
	}
}
