package com.sapient.bookstore.service;

import com.sapient.bookstore.domain.ServiceResponse;
import com.sapient.bookstore.domain.ShoppingCart;

/**
 *
 * @author Pankaj Sharma
 * @since 1.0
 */

public interface ShoppingCartService {
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

	void clearShoppingCart(ShoppingCart shoppingCart);
	ServiceResponse getByUserName(String userName);

}
