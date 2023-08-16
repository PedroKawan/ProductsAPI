package me.pedrokaua.securityproject.models.services;

import jakarta.persistence.EntityNotFoundException;
import me.pedrokaua.securityproject.controllers.ProductController;
import me.pedrokaua.securityproject.dtos.ProductDTO;
import me.pedrokaua.securityproject.exceptions.ProductAlreadyRegisteredException;
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

    public Object findById(String id) throws IllegalArgumentException, EntityNotFoundException{
            Optional<ProductModel> product;
        try {
            product = productRepository.findById(UUID.fromString(id));
            if (product.isEmpty()) {
                throw new EntityNotFoundException("User not found!");
            }

            var productDTO = new ProductDTO(product.get());
            productDTO.add(linkTo(methodOn(ProductController.class).findAllString()).withRel("list"));
            return productDTO;

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public Object saveProduct(ProductModel productModel){
        if (productModel == null) {
            throw new NullPointerException("Entity null");
        }
        if (productRepository.findOne(Example.of(productModel)).isPresent()){
            throw new ProductAlreadyRegisteredException();
        }

        productRepository.save(productModel);
        return productModel;
    }


}
