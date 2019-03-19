package com.sapient.bookstore.service.impl;

import java.util.List;

import com.sapient.bookstore.domain.Book;
import com.sapient.bookstore.domain.BookSearchOption;
import com.sapient.bookstore.domain.ServiceResponse;
import com.sapient.bookstore.repository.BookRepository;
import com.sapient.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Core Service for all book related operations.
 * @author Pankaj Sharma
 * @since 1.0
 */

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> findAll() {
		return (List <Book>)bookRepository.findAll();
	}

	@Override
	public Book findById(Long id) {
		return bookRepository.findById(id).get();
	}

	@Override
	public ServiceResponse blurrySearch(String keyword) {
		List <Book> listBook=bookRepository.findByTitleContaining(keyword);
		if(listBook==null&&listBook.isEmpty())
		{
			return new ServiceResponse(true,"No Books Found try with different string");
		}
		return new ServiceResponse(false,listBook,"Books Found");
	}

	@Override
	public ServiceResponse blurrySearch(BookSearchOption keyword) {
		List <Book> listBook=null;
		switch (keyword.getSearchBy().toLowerCase()) {
			case "title": listBook=bookRepository.findByTitleContaining(keyword.getSearchKeywords());break;
			case "author": listBook=bookRepository.findByAuthor(keyword.getSearchKeywords());break;
			case "price": listBook=bookRepository.findBylistPrice(Double.parseDouble(keyword.getSearchKeywords()));break;
			case "isbn": listBook=bookRepository.findByIsbn(Integer.parseInt(keyword.getSearchKeywords()));break;
			default: return new ServiceResponse(true,"Please mention valid searchBy criteria");
	}
		if(listBook==null||listBook.isEmpty())
		{
			return new ServiceResponse(true,"No Books Found try with different string");
		}
		return new ServiceResponse(false,listBook,"Books Found");
	}
	@Override
	public Book save(Book book) {

		return bookRepository.save(book);
	}
	@Override
	public ServiceResponse addNewBook(Book book) {
		if(book==null&&book.getAuthor()!=null&&book.getAuthor().equals("")
				&&book.getTitle()==null&&book.getTitle().equals(""))
		{
			return new ServiceResponse(true,"All fields are mandatory");
		}
		return new ServiceResponse(false,bookRepository.save(book),"Book Added Successfully");
	}
	@Override
	public ServiceResponse delete(Long bookId) {
		Book book = findById(bookId);
		Book bookToSave= findById(book.getId());
		if(null==bookToSave) {
			new ServiceResponse(true,"No Such Book Found To Delete");
		}
		bookRepository.delete(book);
		return new ServiceResponse(false,bookToSave,"Book Deleted Successfully");
	}

	@Override
	public ServiceResponse updateBook(Book book)
	{
		Book bookToSave= findById(book.getId());
		if(null==bookToSave) {
			new ServiceResponse(true,"No Such Book Found To Update");
		}
		bookToSave.setAuthor(book.getAuthor());
		bookToSave.setBookToCartItemList(book.getBookToCartItemList());
		bookToSave.setIsbn(book.getIsbn());
		bookToSave.setListPrice(book.getListPrice());
		bookToSave.setTitle(book.getTitle());
		save(bookToSave);
		return new ServiceResponse(false,bookToSave,"Book Updated Successfully");
	}
}
