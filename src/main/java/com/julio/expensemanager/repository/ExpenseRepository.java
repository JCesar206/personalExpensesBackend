package com.julio.expensemanager.repository;

import com.julio.expensemanager.model.Expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByUserId(Long userId); // Obtener todos los gastos de un usuario
	List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end); // Obtener gastos entre
	// fechas (para balance mensual)
	
	// Paginación por categoría
	List<Expense> findByUserId(Long userId, Pageable pageable);
	// Filtar por categoría
	List<Expense> findByUserIdAndCategory(Long userId, String category);
	// Estadisticas por categoría
	@Query("""
	SELECT e.category, SUM(e.amount)
	FROM Expense e
	WHERE e.user.id = :userId
	GROUP BY e.category
	""")
	List<Object[]> sumExpensesByCategory(@Param("userId") Long userId);
	
	// Estadísticas por mes
	@Query("""
		SELECT MONTH(e.date), SUM(e.amount)
		FROM Expense e
		WHERE e.user.id = :userId
		GROUP BY MONTH(e.date)
		""")
	List<Object[]> sumExpensesByMonth(@Param("userId") Long userId);
	
	// Total de gastos
	@Query("""
		SELECT SUM(e.amount)
		FROM Expense e
		WHERE e.user.id = :userId
		""")
	Double sumTotalExpenses(@Param("userId") Long userId);
}