CREATE DATABASE IF NOT EXISTS `Ecom_db`;
USE `Ecom_db`;
/*consuemer table*/
CREATE TABLE IF NOT EXISTS`consumer` (
  `con_id` int NOT NULL AUTO_INCREMENT,
  `con_name` varchar(45) DEFAULT NULL,
  `con_password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`con_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/*role table*/
CREATE TABLE IF NOT EXISTS`role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/*cart Products table*/
CREATE TABLE IF NOT EXISTS`cart_product` (
  `cp_id` int NOT NULL AUTO_INCREMENT,
  `quantity` int,
  PRIMARY KEY (`cp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/*Cart table*/
CREATE TABLE IF NOT EXISTS`cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `total_amount` double,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/*Product table*/
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) DEFAULT NULL,
  `price` Double,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/*Category table*/
CREATE TABLE IF NOT EXISTS`category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

select * from consumer;
select * from role;
select * from cart_product;
select * from cart;
select * from product;
select * from category;


/*SET FOREIGN_KEY_CHECKS=0; DROP TABLE user_details; SET FOREIGN_KEY_CHECKS=1
drop table consumer;
drop table role;
drop table cart_product;
drop table cart;
drop table product;
drop table category;*/
select * from user_cart