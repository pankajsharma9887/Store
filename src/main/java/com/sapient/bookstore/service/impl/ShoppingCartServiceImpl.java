package com.sapient.bookstore.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.sapient.bookstore.domain.CartItem;
import com.sapient.bookstore.domain.ServiceResponse;
import com.sapient.bookstore.domain.ShoppingCart;
import com.sapient.bookstore.domain.User;
import com.sapient.bookstore.repository.ShoppingCartRepository;
import com.sapient.bookstore.service.CartItemService;
import com.sapient.bookstore.service.ShoppingCartService;
import com.sapient.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Core Service for all shopping cart related operations.
 * @author Pankaj Sharma
 * @since 1.0
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private UserService userService;

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Override
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		BigDecimal cartTotal = new BigDecimal(0);
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		if(cartItemList!=null&&!cartItemList.isEmpty())
		{
			for (CartItem cartItem:cartItemList)
				cartTotal=cartTotal.add(cartItem.getSubtotal());
		}
		shoppingCart.setGrandTotal(cartTotal);
		shoppingCartRepository.save(shoppingCart);
		return shoppingCart;
		
	}

	@Override
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		for (CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			cartItemService.save(cartItem);
		}
		shoppingCart.setGrandTotal(new BigDecimal(0));
		shoppingCartRepository.save(shoppingCart);
	}

	@Override
    public ServiceResponse getByUserName(String userName)
	{
		User user=userService.findByUsername(userName);
		if(null==user)
		{
			return new ServiceResponse(true,"Invalid user");
		}
		return new ServiceResponse(false,user.getShoppingCart(),"Shopping cart found successfully");
	}
}
