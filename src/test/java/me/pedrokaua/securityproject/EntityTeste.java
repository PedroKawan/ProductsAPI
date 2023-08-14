package me.pedrokaua.securityproject;

import me.pedrokaua.securityproject.controllers.ProductController;
import me.pedrokaua.securityproject.controllers.UserController;
import me.pedrokaua.securityproject.dtos.ProductDTO;
import me.pedrokaua.securityproject.exceptions.UserAlreadyExistsException;
import me.pedrokaua.securityproject.models.entities.ProductModel;
import me.pedrokaua.securityproject.models.entities.UserModel;
import me.pedrokaua.securityproject.models.repositories.ProductRepository;
import me.pedrokaua.securityproject.models.repositories.UserRepository;
import me.pedrokaua.securityproject.models.services.ProductService;
import me.pedrokaua.securityproject.models.services.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntityTeste {

	@Autowired
	ProductController productController;
	@Autowired
	ProductService productService;
	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserController userController;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;


	@Test
	@Order(0)
	void createEntities(){
		assertDoesNotThrow(() -> {
			ProductModel p = new ProductModel(UUID.randomUUID(), "Pen", 1.5);
			ProductDTO pDTO = new ProductDTO(p);
			UserModel u = new UserModel("Pedro", "123456", "ADMIN");
		});
	}

	@Test
	@Order(1)
	void saveProducts(){
		assertDoesNotThrow(() -> {
			UUID uuid = UUID.fromString("a0afffaa-9f2c-4842-9988-ff351ed38b92");
			ProductModel product = new ProductModel(uuid, "Notebook", 2000.00);
			productController.saveProduct(product);
		});
	}


	@Test
	@Order(2)
	void saveUsers(){
		UUID uuid = UUID.fromString("b5fffffd-9f2c-4235-6600-ff525ed38b92");
		UserModel user = new UserModel(uuid, "Gekkou", "123456", "ADMIN");

		assertDoesNotThrow(() ->{
			userService.saveUser(user);
		});
		assertThrowsExactly(NullPointerException.class, () -> {
			userService.saveUser(null);
		});

		new Thread(() -> {
			assertThrowsExactly(UserAlreadyExistsException.class, () -> {
				userService.saveUser(user);
			});
		});
	}

	@Test
	@Order(3)
	void findProductAndUser(){
		new Thread(() -> {
			/* PRODUCT */
			UUID uuidP = UUID.fromString("a0afffaa-9f2c-4842-9988-ff351ed38b92");
			ProductModel product = new ProductModel(uuidP, "Notebook", 2000.00);

			assertDoesNotThrow(()-> {
				product.equals(productController.findById(uuidP.toString()));
			});

			/* USER */
			UUID uuidU = UUID.fromString("b5fffffd-9f2c-4235-6600-ff525ed38b92");
			UserModel user = new UserModel(uuidU, "Gekkou", "123456", "ADMIN");

			assertDoesNotThrow(()-> {
				user.equals(userController.findByName("Gekkou"));
			});

		}).start();
	}

	@Test
	@Order(4)
	void delete(){
		SwingUtilities.invokeLater(() -> {
			userRepository.deleteById(UUID.fromString("b5fffffd-9f2c-4235-6600-ff525ed38b92"));
			productRepository.deleteById(UUID.fromString("a0afffaa-9f2c-4842-9988-ff351ed38b92"));
		});
	}
}
