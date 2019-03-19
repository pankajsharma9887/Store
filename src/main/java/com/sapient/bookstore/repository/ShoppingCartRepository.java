package com.sapient.bookstore.repository;

import com.sapient.bookstore.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA implementation for data access
 * @author Pankaj Sharma
 * @since 1.0
 */


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
