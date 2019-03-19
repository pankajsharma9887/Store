package com.sapient.bookstore.service;

import com.sapient.bookstore.domain.Book;
import com.sapient.bookstore.domain.BookSearchOption;
import com.sapient.bookstore.domain.ServiceResponse;

import java.util.List;
/**
 *
 * @author Pankaj Sharma
 * @since 1.0
 */

public interface BookService {
	
	List<Book> findAll();
	
	Book findById(Long id);

	ServiceResponse blurrySearch(String keyword);

	Book save(Book user);

	ServiceResponse delete(Long bookId);

	ServiceResponse updateBook(Book book);

	ServiceResponse addNewBook(Book book);

	ServiceResponse blurrySearch(BookSearchOption keyword);
}
