package Spb.Ecom.models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Product")
public class Product {
	@Id
	@Column(name="product_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer productId;
	
	@Column(name="product_name")
	private String productName;
	
	@Column (name="price")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	Category category;
	
	@ManyToOne
	@JoinColumn(name="seller_id")
	@JsonBackReference
	User seller;
	
	@OneToMany(mappedBy ="product",fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonIgnore
	List<CartProducts> cartProduct;
	
	
	
	public List<CartProducts> getCartProduct() {
		return cartProduct;
	}
	public void setCartProduct(List<CartProducts> cartProduct) {
		this.cartProduct = cartProduct;
	}
	public Product(Integer productId, String productName, Double price, Category category, User seller) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.category = category;
		this.seller = seller;
	}
	public Product(String productName, Double price, Category category, User seller) {
		super();
		this.productName = productName;
		this.price = price;
		this.category = category;
		this.seller = seller;
	}
	public Product() {
		
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}
}
