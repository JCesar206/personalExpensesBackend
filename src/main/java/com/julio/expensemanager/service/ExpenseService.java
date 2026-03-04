package com.julio.expensemanager.service;

import com.julio.expensemanager.model.Expense;
import com.julio.expensemanager.model.User;
import com.julio.expensemanager.repository.ExpenseRepository;
import com.julio.expensemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
	
	private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);
	
	private final ExpenseRepository expenseRepository;
	private final UserRepository userRepository;
	
	public Expense createExpense(Long userId, Expense expense) {
		log.info("Creating expense for userId: {}", userId);
		
		User user = userRepository.findById(userId)
			.orElseThrow(() -> {
				log.error("User not found with id {}", userId);
				return new RuntimeException("User not found");
			});
		
		expense.setUser(user);
		Expense saved = expenseRepository.save(expense);
		
		log.info("Expense created with id {}", saved.getId());
		return saved;
	}
	
	public List<Expense> getAllByUser(Long userId) {
		log.info("Fetching expenses for userId {}", userId);
		return expenseRepository.findByUserId(userId);
	}
	
	public Expense updateExpense(Long expenseId, Expense updatedExpense) {
		log.info("Updating expense with id {}", expenseId);
		
		Expense expense = expenseRepository.findById(expenseId)
			.orElseThrow(() -> {
				log.error("Expense not found with id {}", expenseId);
				return new RuntimeException("Expense not found");
			});
		
		expense.setDescription(updatedExpense.getDescription());
		expense.setAmount(updatedExpense.getAmount());
		expense.setCategory(updatedExpense.getCategory());
		expense.setDate(updatedExpense.getDate());
		
		Expense saved = expenseRepository.save(expense);
		
		log.info("Expense updated successfully id {}", expenseId);
		return saved;
	}
	
	public void deleteExpense(Long expenseId) {
		log.info("Deleting expense with id {}", expenseId);
		
		if (!expenseRepository.existsById(expenseId)) {
			log.error("Expense not found for deletion id {}", expenseId);
			throw new RuntimeException("Expense not found");
		}
		
		expenseRepository.deleteById(expenseId);
		log.info("Expense deleted id {}", expenseId);
	}
	
	public Double getMonthlyBalance(Long userId, int year, int month) {
		log.info("Calculating monthly balance for user {} - {}/{}", userId, month, year);
		
		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
		
		List<Expense> expenses = expenseRepository
			.findByUserIdAndDateBetween(userId, start, end);
		
		double total = expenses.stream()
			.mapToDouble(Expense::getAmount)
			.sum();
		
		log.info("Monthly balance calculated: {}", total);
		return total;
	}
}