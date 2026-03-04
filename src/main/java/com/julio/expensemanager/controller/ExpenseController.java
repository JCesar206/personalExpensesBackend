package com.julio.expensemanager.controller;

import com.julio.expensemanager.model.Expense;
import com.julio.expensemanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
	
	private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);
	
	private final ExpenseService expenseService;
	
	@PostMapping("/{userId}")
	public Expense create(@PathVariable Long userId,
																							@RequestBody Expense expense) {
		log.info("REST request to create expense");
		return expenseService.createExpense(userId, expense);
	}
	
	@GetMapping("/{userId}")
	public List<Expense> getAll(@PathVariable Long userId) {
		log.info("REST request to get expenses");
		return expenseService.getAllByUser(userId);
	}
	
	@PutMapping("/{expenseId}")
	public Expense update(@PathVariable Long expenseId,
																							@RequestBody Expense expense) {
		log.info("REST request to update expense");
		return expenseService.updateExpense(expenseId, expense);
	}
	
	@DeleteMapping("/{expenseId}")
	public void delete(@PathVariable Long expenseId) {
		log.info("REST request to delete expense");
		expenseService.deleteExpense(expenseId);
	}
	
	@GetMapping("/balance/{userId}")
	public Double getBalance(@PathVariable Long userId,
																										@RequestParam int year,
																										@RequestParam int month) {
		log.info("REST request to calculate monthly balance");
		return expenseService.getMonthlyBalance(userId, year, month);
	}
}