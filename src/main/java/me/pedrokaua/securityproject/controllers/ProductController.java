package me.pedrokaua.securityproject.controllers;

import jakarta.validation.Valid;
import me.pedrokaua.securityproject.dtos.ProductDTO;
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
    public @ResponseBody ResponseEntity<Object> findById(@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductModel entity){
        return ResponseEntity.status(HttpStatus.OK).body(productService.saveProduct(entity));
    }
}
