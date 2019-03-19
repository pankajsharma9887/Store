package com.sapient.bookstore.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.sapient.bookstore.domain.*;
import com.sapient.bookstore.repository.BookToCartItemRepository;
import com.sapient.bookstore.repository.CartItemRepository;
import com.sapient.bookstore.service.BookService;
import com.sapient.bookstore.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Core Service for all cart item related operations.
 * @author Pankaj Sharma
 * @since 1.0
 */

@Service
public class CartItemServiceImpl implements CartItemService {


	@Autowired
	private BookService bookService;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private BookToCartItemRepository bookToCartItemRepository;
	
	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getListPrice()).multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
		
	}

	@Override
	public CartItem addBookToCartItem(Book book, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem : cartItemList) {
			if(book.getId() == cartItem.getBook().getId()) {
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSubtotal(new BigDecimal(book.getListPrice()).multiply(new BigDecimal(qty)).add(cartItem.getSubtotal()));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);
		
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(book.getListPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);
		
		BookToCartItem bookToCartItem = new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		bookToCartItemRepository.save(bookToCartItem);
		
		return cartItem;
		
	}

	@Override
	public CartItem findById(Long cartItemId) {
		return cartItemRepository.findById(cartItemId).orElse(null);
	}

	@Override
	public void removeCartItem(CartItem cartItem) {
		bookToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	@Override
	public List<CartItem> findByOrder(Order order) {
		return cartItemRepository.findByOrder(order);
	}


	@Override
	public ServiceResponse addBookToCartItem(Long bookId, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		Book book = bookService.findById(bookId);
		for (CartItem cartItem : cartItemList) {
			if(bookId == cartItem.getBook().getId()) {
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSubtotal(new BigDecimal(book.getListPrice()).multiply(new BigDecimal(qty)).add(cartItem.getSubtotal()));
				cartItemRepository.save(cartItem);
				return new ServiceResponse(false,cartItem,"Cart Item updated successfully");
			}
		}

		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);

		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(book.getListPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);

		BookToCartItem bookToCartItem = new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		bookToCartItemRepository.save(bookToCartItem);

		return new ServiceResponse(false,cartItem,"Cart Item created successfully");

	}
}
