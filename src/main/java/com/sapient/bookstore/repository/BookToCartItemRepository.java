package com.sapient.bookstore.repository;

import com.sapient.bookstore.domain.BookToCartItem;
import com.sapient.bookstore.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * JPA implementation for data access
 * @author Pankaj Sharma
 * @since 1.0
 */


@Transactional
public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long> {

	void deleteByCartItem(CartItem cartItem);

}
