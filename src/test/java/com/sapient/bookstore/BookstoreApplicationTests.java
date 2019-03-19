package com.sapient.bookstore;

import com.sapient.bookstore.domain.Book;
import com.sapient.bookstore.domain.BookSearchOption;
import com.sapient.bookstore.domain.User;
import com.sapient.bookstore.domain.security.Role;
import com.sapient.bookstore.domain.security.UserRole;
import com.sapient.bookstore.service.BookService;
import com.sapient.bookstore.service.UserService;
import com.sapient.bookstore.utility.SecurityUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class BookstoreApplicationTests {
	@Autowired
	UserService userService;
	@Autowired
	private BookService bookService;
	@Before
	public void setupData()
	{
	createUser();
	addBook();
	}

	@Test
	public void contextLoads() {
	}

	public void createUser()
	{
		try {
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
		}
		catch (Exception e)
		{
			Assert.fail("Unable to creat new user : Part setup Data");
		}
	}

	public void addBook() {
		Book book =new Book();
		book.setTitle("TestBookTitle");
		book.setListPrice(11);
		book.setAuthor("AuthorPankaj");
		book.setIsbn(12345678);
		bookService.addNewBook(book);
		BookSearchOption bookSearchOption=new BookSearchOption();
		bookSearchOption.setSearchBy("isbn");
		bookSearchOption.setSearchKeywords("12345678");
		List<Book> booksaved=(List<Book>)bookService.blurrySearch(bookSearchOption).getData();
		Assert.assertEquals(book.getIsbn(),booksaved.get(0).getIsbn());
	}
}
