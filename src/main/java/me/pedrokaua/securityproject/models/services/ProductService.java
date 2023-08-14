package me.pedrokaua.securityproject.models.services;

import me.pedrokaua.securityproject.controllers.ProductController;
import me.pedrokaua.securityproject.dtos.ProductDTO;
import me.pedrokaua.securityproject.models.entities.ProductModel;
import me.pedrokaua.securityproject.models.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> findAll(){
        return productRepository.findAll();
    }

    public List<ProductDTO> findAllString(){
        List<ProductDTO> list = productRepository.findAllString()
                .stream()
                .map(ProductDTO::new)
                .map(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withRel("product")))
                .toList();

        return list;
    }

    public Object findById(String id){
        Optional<ProductModel> product = Optional.empty();
        try {
            product = productRepository.findById(UUID.fromString(id));

            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found By Id: " + id);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Request Error! Id Not Acceptable.");
        }
        var productDTO = new ProductDTO(product.get());
        productDTO.add(linkTo(methodOn(ProductController.class).findAllString()).withRel("list"));

        return productDTO;
    }

    public Object saveProduct(ProductModel productModel){
        if (productModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in Request-Body of the Product!");
        }
        if (productRepository.findOne(Example.of(productModel)).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Already Exists!");
        }

        productRepository.save(productModel);
        return productModel;
    }


}
