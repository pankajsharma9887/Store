package com.sapient.bookstore;

import com.sapient.bookstore.domain.Book;
import com.sapient.bookstore.domain.User;
import com.sapient.bookstore.domain.security.Role;
import com.sapient.bookstore.domain.security.UserRole;
import com.sapient.bookstore.service.BookService;
import com.sapient.bookstore.service.UserService;
import com.sapient.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;

	@Autowired
	private  BookService bookService;
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("adminName");
		user1.setLastName("adminLastName");
		user1.setUsername("adminUser");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		user1.setEmail("pankaj.sharma6@apient.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		userService.createUser(user1, userRoles);

		Book book =new Book();
		book.setTitle("TestBookTitle");
		book.setListPrice(11);
		book.setAuthor("AuthorPankaj");
		book.setIsbn(12345678);
		bookService.addNewBook(book);

	}
}
