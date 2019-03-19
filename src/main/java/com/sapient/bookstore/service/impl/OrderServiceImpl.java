package com.sapient.bookstore.service.impl;

import java.util.Calendar;
import java.util.List;

import com.sapient.bookstore.domain.*;
import com.sapient.bookstore.repository.OrderRepository;
import com.sapient.bookstore.service.CartItemService;
import com.sapient.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Core Service for order related operations.
 * @author Pankaj Sharma
 * @since 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartItemService cartItemService;

	@Override
	public synchronized Order createOrder(ShoppingCart shoppingCart, User user) {
		
		Order order = new Order();
		order.setOrderStatus("created");
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		for(CartItem cartItem : cartItemList) {
			Book book = cartItem.getBook();
			cartItem.setOrder(order);
		}
		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		order.setUser(user);
		order = orderRepository.save(order);
		return order;
	}

	@Override
	public Order findOne(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

}
