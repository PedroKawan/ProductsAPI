package me.pedrokaua.securityproject.controllers;

import jakarta.validation.Valid;
import me.pedrokaua.securityproject.dtos.ProductDTO;
import me.pedrokaua.securityproject.entities.ProductModel;
import me.pedrokaua.securityproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return productService.findAll();
    }

    @GetMapping("/records")
    public @ResponseBody ResponseEntity<List<ProductDTO>> findAllString(){
        return productService.findAllString();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> findById(@PathVariable(value = "id") String id){
        return productService.findById(id);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductModel entity){
        return productService.saveProduct(entity);
    }
}
