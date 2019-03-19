package com.sapient.bookstore.controller;
import com.sapient.bookstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sapient.bookstore.service.BookService;
import com.sapient.bookstore.service.CartItemService;
import com.sapient.bookstore.service.ShoppingCartService;
import com.sapient.bookstore.service.UserService;

/**
 * Rest Controller for shopping cart related requests
 * @author Pankaj Sharma
 * @since 1.0
 */

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartService shoppingCartService ;

	@Autowired
	private CartItemService cartItemService;

	/**
	 * Rest endpoint for getting a cart detail of user by ID
	 * @param userName
	 * @return
	 */
    @RequestMapping(value = "/cart",method = RequestMethod.GET)
	public ResponseEntity shoppingCart(@RequestHeader("userName") String userName) {
		try {
			ServiceResponse serviceResponse=shoppingCartService.getByUserName(userName);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity<>(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * Rest endPoint for inserting a new item in shopping cart
	 *
	 * @param userName
	 * @param bookId
	 * @param quantity
	 * @return
	 */
	@RequestMapping(value="/item", method=RequestMethod.POST)
	public ResponseEntity addItem(
			@RequestHeader("userName") String userName, @RequestParam("bookId") Long bookId,
			@RequestParam("quantity") Integer quantity
			) {
		try {
			User user =userService.findByUsername(userName);
			if (null==user)
				return new ResponseEntity("Invald User", HttpStatus.BAD_REQUEST);
			ServiceResponse serviceResponse=cartItemService.addBookToCartItem(bookId,user ,quantity);
			if (serviceResponse.hasError())
			{
				return new ResponseEntity(serviceResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(shoppingCartService.getByUserName(userName), HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Rest endpoint for updating an item in shopping cart
	 *
	 * @param userName
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	@RequestMapping(value="/item", method=RequestMethod.PUT)
	public ResponseEntity updateShoppingCart(
			@RequestHeader("userName") String userName,
			@RequestParam("cartItemId") Long cartItemId,
			@RequestParam("cartItemId") int quantity
			) {
		try {
			User user=userService.findByUsername(userName);
			if(null==user)
				new ResponseEntity("Invalid user",HttpStatus.INTERNAL_SERVER_ERROR);
			CartItem cartItem = cartItemService.findById(cartItemId);
			cartItem.setQty(quantity);
			cartItemService.updateCartItem(cartItem);
			return new ResponseEntity(user.getShoppingCart(),HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Rest end point for deleting an item from shopping cart
	 * @param userName
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/item", method=RequestMethod.DELETE)
	public ResponseEntity removeItem(@RequestHeader("userName") String userName,
			@RequestParam("id") Long id) {
    	try {
			User user=userService.findByUsername(userName);
			if(null==user)
				new ResponseEntity("Invalid user",HttpStatus.INTERNAL_SERVER_ERROR);
			cartItemService.removeCartItem(cartItemService.findById(id));
			return new ResponseEntity(user.getShoppingCart(),HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}
