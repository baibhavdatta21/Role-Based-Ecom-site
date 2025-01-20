package Spb.Ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Spb.Ecom.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
