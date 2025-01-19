package Spb.Ecom.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="consumer")
public class User {
	@Id
	@Column(name="con_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer conId;
	
	@Column(name="con_name")
	private String userName;
	
	@Column(name="con_password")
	private String userPassword;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
				name="user_cart"
				,joinColumns = @JoinColumn(name="role_id")
				,inverseJoinColumns=@JoinColumn(name="user_id"))
	List<Role> role;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true) 
	@JsonManagedReference
	Cart cart;
	
	@OneToMany(mappedBy="seller",fetch=FetchType.EAGER)
//	@JsonBackReference 
	@JsonManagedReference
	List<Product> product;

	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	public User(Integer conId, String userName, String userPassword, List<Role> role) {
		super();
		this.conId = conId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.role = role;
	}
	public User() {
		
	}
	public User(String userName, String userPassword, List<Role> role) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.role = role;
	}
	public User(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}
	public Integer getConId() {
		return conId;
	}
	public void setConId(Integer conId) {
		this.conId = conId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public List<Role> getRole() {
		return role;
	}
	public void setRole(List<Role> role) {
		this.role = role;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
}
