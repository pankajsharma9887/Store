package com.sapient.bookstore.controller;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.*;

import com.sapient.bookstore.domain.BookSearchOption;
import com.sapient.bookstore.domain.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sapient.bookstore.domain.Book;
import com.sapient.bookstore.domain.User;
import com.sapient.bookstore.domain.security.Role;
import com.sapient.bookstore.domain.security.UserRole;
import com.sapient.bookstore.service.BookService;
import com.sapient.bookstore.service.CartItemService;
import com.sapient.bookstore.service.UserService;
import com.sapient.bookstore.utility.SecurityUtility;
/**
 * Rest Controller for user data/update related requests
 * @author Pankaj Sharma
 * @since 1.0
 */
@RestController
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	
	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	/**
	 * Rest end point for updating a user
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user", method=RequestMethod.PUT)
	public ResponseEntity updateUserInfo(@RequestBody User user) throws Exception {
		try {
			ServiceResponse serviceResponse=userService.updateUser(user);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
		return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	/**
	 * Rest end point for creating s new user
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user", method = RequestMethod.POST)
	public ResponseEntity addNewUser(@RequestBody User user) throws Exception{
		try {
			user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
			user.setEmail("pankaj.sharma6@apient.com");
			Set<UserRole> userRoles = new HashSet<>();
			Role role = new Role();
			role.setName("ROLE_USER");
			userRoles.add(new UserRole(user, role));
			ServiceResponse serviceResponse=userService.createUser(user, userRoles);;
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Rest end point for getting a book details by Id.
	 * @param bookId
	 * @return
	 */
	@RequestMapping(value = "/book/{id}",method = RequestMethod.GET)
	public ResponseEntity getBook(
			@PathVariable("id") String bookId) {
		try {
			ServiceResponse serviceResponse=bookService.blurrySearch(bookId);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Rest end point for searching a book based on criteria .
	 * @param searchBy
	 * @param searchKeyword
	 * @return
	 */
	@RequestMapping(value = "/searchBook",method = RequestMethod.GET)
	public ResponseEntity getBookBy(
			@RequestParam(value = "searchBy")String searchBy,@RequestParam(value = "searchKeyword") String searchKeyword) {
		try {

			if(searchBy==null&&searchBy.equals("")&&searchKeyword==null&&searchKeyword.equals(""))
			{
				return new ResponseEntity("Search Keyword/Search By cannot be null",HttpStatus.BAD_REQUEST);
				}
			if(!BookSearchOption.SearchBy.AUTHOR.getValue().equals(searchBy)
					&&!BookSearchOption.SearchBy.PRICE.getValue().equals(searchBy)
					&&!BookSearchOption.SearchBy.ISBN.getValue().equals(searchBy)
					&&!BookSearchOption.SearchBy.TITLE.getValue().equals(searchBy))
			{
				return new ResponseEntity("Please use One of these values in search by"
						+BookSearchOption.SearchBy.TITLE.getValue()
						+","+BookSearchOption.SearchBy.ISBN.getValue()
						+","+BookSearchOption.SearchBy.PRICE.getValue()
						+","+BookSearchOption.SearchBy.AUTHOR.getValue()
						,HttpStatus.BAD_REQUEST);
			}
				BookSearchOption bookSearchOption= new BookSearchOption();
				bookSearchOption.setSearchBy(searchBy);
				bookSearchOption.setSearchKeywords(searchKeyword);
			ServiceResponse serviceResponse=bookService.blurrySearch(bookSearchOption);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Rest end point for deleting a book.
	 * @param bookId
	 * @return
	 */
	@RequestMapping(value = "/book/{id}",method = RequestMethod.DELETE)
	public ResponseEntity deleteBook(
			@PathVariable("id") Long bookId) {
		try {
			ServiceResponse serviceResponse=bookService.delete(bookId);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Rest end point for inserting a new book.
	 * @param book
	 * @return
	 */
	@RequestMapping(value = "/book",method = RequestMethod.POST)
	public ResponseEntity addBook(
			@RequestBody Book book) {
		try {
			ServiceResponse serviceResponse=bookService.addNewBook(book);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Rest end point for updating a book .
	 * @param book
	 * @return
	 */
	@RequestMapping(value = "/book",method = RequestMethod.PUT)
	public ResponseEntity updateBook(
			@RequestBody Book book) {

		try {
			ServiceResponse serviceResponse=bookService.updateBook(book);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
