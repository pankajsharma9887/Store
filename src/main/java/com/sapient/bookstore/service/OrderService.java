package com.sapient.bookstore.service;


import com.sapient.bookstore.domain.*;
/**
 *
 * @author Pankaj Sharma
 * @since 1.0
 */

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, User user);

	Order findOne(Long id);
}
