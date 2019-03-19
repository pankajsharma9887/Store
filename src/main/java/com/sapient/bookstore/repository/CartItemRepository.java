package com.sapient.bookstore.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.sapient.bookstore.domain.CartItem;
import com.sapient.bookstore.domain.Order;
import com.sapient.bookstore.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
/**
 * JPA implementation for data access
 * @author Pankaj Sharma
 * @since 1.0
 */



@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
 
	List<CartItem> findByOrder(Order order);

}
