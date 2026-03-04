package com.julio.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.julio.expensemanager.model.Expense;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByUserId(Long userId);
	List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}