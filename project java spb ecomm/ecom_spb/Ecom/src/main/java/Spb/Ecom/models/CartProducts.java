package Spb.Ecom.models;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cart_product")
public class CartProducts {
	
	@Id
	@Column(name="cp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cpId;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name="cart_id")
	@JsonBackReference
	Cart cart;
	
	@ManyToOne()
	@JoinColumn(name="product_id")
	Product product;

	public CartProducts(Integer cpId, Integer quantity, Cart cart, Product product) {
		super();
		this.cpId = cpId;
		this.quantity = quantity;
		this.cart = cart;
		this.product = product;
	}
	public CartProducts(Integer quantity, Cart cart, Product product) {
		super();
		this.quantity = quantity;
		this.cart = cart;
		this.product = product;
	}
	public CartProducts() {
	}
	public Integer getCpId() {
		return cpId;
	}
	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
