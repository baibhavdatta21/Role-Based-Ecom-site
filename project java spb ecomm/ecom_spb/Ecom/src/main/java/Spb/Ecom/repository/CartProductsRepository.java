package Spb.Ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Spb.Ecom.models.CartProducts;

public interface CartProductsRepository extends JpaRepository<CartProducts,Integer> {

}
