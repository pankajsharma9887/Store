package com.sapient.bookstore.repository;

import com.sapient.bookstore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
/**
 * JPA implementation for data access
 * @author Pankaj Sharma
 * @since 1.0
 */

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByTitleContaining(String keyword);

	List<Book> findBylistPrice(double price);

	List<Book> findByAuthor(String author);

	List<Book> findByIsbn(int isbn);
}
