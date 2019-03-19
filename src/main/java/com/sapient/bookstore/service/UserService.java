package com.sapient.bookstore.service;

import java.util.Set;
import com.sapient.bookstore.domain.*;
import com.sapient.bookstore.domain.security.UserRole;
/**
 *
 * @author Pankaj Sharma
 * @since 1.0
 */

public interface UserService {
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	ServiceResponse createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);

	User findById(Long id);

	ServiceResponse updateUser(User user);
	
}
