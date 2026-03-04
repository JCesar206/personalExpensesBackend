package com.julio.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.julio.expensemanager.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}