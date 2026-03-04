package com.julio.expensemanager.config;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import com.julio.expensemanager.repository.*;
import com.julio.expensemanager.model.*;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer {
	private final UserRepository userRepository;
	private final ExpenseRepository expenseRepository;
	
	@PostConstruct
	public void init() {
		if(userRepository.count() > 0) return;;
		
		User user = User.builder()
			.email("test@test.com")
			.password("123456")
			.role("USER")
			.build();
		
		userRepository.save(user);
		
		expenseRepository.save(
			Expense.builder()
				.description("Supermercado")
				.amount(1200.0)
				.date(LocalDate.now())
				.user(user)
				.build()
		);
	}
}