package com.sapient.bookstore.service.impl;



import com.sapient.bookstore.domain.*;
import com.sapient.bookstore.domain.security.PasswordResetToken;
import com.sapient.bookstore.domain.security.UserRole;
import com.sapient.bookstore.repository.*;
import com.sapient.bookstore.service.UserService;
import com.sapient.bookstore.utility.SecurityUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
/**
 * Core Service for all user related operations.
 * @author Pankaj Sharma
 * @since 1.0
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Used to creat a new user and save it to DB
	 * @param user
	 * @param userRoles
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public ServiceResponse createUser(User user, Set<UserRole> userRoles) throws Exception{
		if(user==null||user.getUsername()==null||user.getUsername().equals("")
				||user.getPassword()==null&&user.getPassword().equals("")
				||user.getFirstName()==null&&user.getFirstName().equals(""))
		{
			return new ServiceResponse(true,"Values cannot be null");
		}
		User user1 = userRepository.findByUsername(user.getUsername());
		if(user1 != null) {
			LOG.info("user {} already exists", user.getUsername());
			return new ServiceResponse(true,"User Already exist");
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());

			}
			user.getUserRoles().addAll(userRoles);
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			user.setShoppingCart(shoppingCart);
			user1 = userRepository.save(user);
			LOG.info("user save finished");
		}
		
		return new ServiceResponse(false,user1,"User Created Successfully");

	}

	/**
	 * Used to save a user.
	 * @param user
	 * @return
	 */
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * Used to find a user.
	 * @param id
	 * @return
	 */
	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	/**
	 * Method is used for updating a user details.
	 * @param user
	 * @return
	 */
	@Override
	public  ServiceResponse updateUser(User user)
	{
		User currentUser = userRepository.findByUsername(user.getUsername());
		if(currentUser == null) {
			return new ServiceResponse(true,"No user found to update");
		}
		if (userRepository.findByEmail(user.getEmail())!=null) {
			if(userRepository.findByEmail(user.getEmail()).getId() == currentUser.getId()) {
				return new ServiceResponse(true,"Email already registered");
			}
		}
		if (user.getPassword() != null && !user.getPassword().isEmpty() && !user.getPassword().equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		userRepository.save(currentUser);
		return new ServiceResponse(false,user,"User Update Successfully");
	}
}
