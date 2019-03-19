package com.sapient.bookstore.service;

import com.sapient.bookstore.domain.*;

import java.util.List;

public interface CartItemService {
	
	CartItem findById(Long cartItemId);

	void removeCartItem(CartItem findById);
	
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	CartItem updateCartItem(CartItem cartItem);

	CartItem addBookToCartItem(Book book, User user, int parseInt);

	CartItem save(CartItem cartItem);

	List<CartItem> findByOrder(Order order);

	ServiceResponse addBookToCartItem(Long bookId, User user, int qty);

}
