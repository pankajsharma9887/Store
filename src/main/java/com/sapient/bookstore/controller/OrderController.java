package com.sapient.bookstore.controller;

import com.sapient.bookstore.domain.CartItem;
import com.sapient.bookstore.domain.Order;
import com.sapient.bookstore.domain.ShoppingCart;
import com.sapient.bookstore.domain.User;
import com.sapient.bookstore.service.CartItemService;
import com.sapient.bookstore.service.OrderService;
import com.sapient.bookstore.service.ShoppingCartService;
import com.sapient.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * Rest Controller for order related requests
 * @author Pankaj Sharma
 * @since 1.0
 */
@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

    /**
     * Method restend point for submitting an order
     * @param userName
     * @param cartId
     * @return
     */
	@RequestMapping(value="/order",method = RequestMethod.POST)
	public ResponseEntity submitOrder(
            @RequestHeader("userName") String userName,
			@RequestParam("id") Long cartId) {
	    try {
            User user = userService.findByUsername(userName);
            if (null == user && cartId != user.getShoppingCart().getId()) {
                return new ResponseEntity("Invalid User or invalid cart Id", HttpStatus.BAD_REQUEST);
            }
            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());
            if (cartItemList.size() == 0) {
                return new ResponseEntity("Cart is empty", HttpStatus.BAD_REQUEST);
            }

            ShoppingCart shoppingCart = user.getShoppingCart();
            Order order = orderService.createOrder(shoppingCart, user);
            shoppingCartService.clearShoppingCart(shoppingCart);
            return new ResponseEntity(order, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

	}

    /**
     *  Rest end point for getting an order detail by Id
     * @param orderId
     * @param userName
     * @return
     */
    @RequestMapping(value="/order",method = RequestMethod.GET)
	public ResponseEntity orderDetail(
			@RequestParam("id") Long orderId,
			  @RequestHeader("userName") String userName
			){
        try {
            User user = userService.findByUsername(userName);
            if (null == user) {
                return new ResponseEntity("Invalid User or invalid cart Id", HttpStatus.BAD_REQUEST);
            }
            Order order = orderService.findOne(orderId);

            if (order.getUser().getId() != user.getId()) {
                return new ResponseEntity("Invalid order Id", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity(order, HttpStatus.BAD_REQUEST);
            }
        }
		catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}



}
