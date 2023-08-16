package me.pedrokaua.securityproject.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import me.pedrokaua.securityproject.dtos.ProductDTO;
import me.pedrokaua.securityproject.exceptions.ProductAlreadyRegisteredException;
import me.pedrokaua.securityproject.models.entities.ProductModel;
import me.pedrokaua.securityproject.models.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public @ResponseBody ResponseEntity<List<ProductModel>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/records")
    public @ResponseBody ResponseEntity<List<ProductDTO>> findAllString(){
        return  ResponseEntity.status(HttpStatus.OK).body(productService.findAllString());
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> findById(@PathVariable(value = "id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found By Id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }


    @PostMapping
    public @ResponseBody ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductModel entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.saveProduct(entity));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in Request-Body of the Product!");
        } catch (ProductAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Already Exists!");
        }
    }
}
