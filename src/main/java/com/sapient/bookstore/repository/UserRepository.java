package com.sapient.bookstore.repository;

import com.sapient.bookstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * JPA implementation for data access
 * @author Pankaj Sharma
 * @since 1.0
 */

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findByEmail(String email);
}
