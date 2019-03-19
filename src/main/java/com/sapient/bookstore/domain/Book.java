package com.sapient.bookstore.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Model
 * @author Pankaj Sharma
 * @since 1.0
 */
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String author;
	private int isbn;
	private double listPrice;

	@OneToMany(mappedBy = "book")
	@JsonIgnore
	private List<BookToCartItem> bookToCartItemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public double getListPrice() {
		return listPrice;
	}

	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	public List<BookToCartItem> getBookToCartItemList() {
		return bookToCartItemList;
	}

	public void setBookToCartItemList(List<BookToCartItem> bookToCartItemList) {
		this.bookToCartItemList = bookToCartItemList;
	}
	
	
}
