package me.pedrokaua.securityproject.dtos;

import me.pedrokaua.securityproject.entities.ProductModel;
import me.pedrokaua.securityproject.projections.ProductProjection;
import org.springframework.hateoas.RepresentationModel;

public class ProductDTO extends RepresentationModel<ProductDTO> {
    private String id;
    private String name;
    private Double value;

    public ProductDTO(String id, String name, Double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public ProductDTO(ProductModel model) {
        this.id = model.getId().toString();
        this.name = model.getName();
        this.value = model.getValue();
    }

    public ProductDTO(ProductProjection projection) {
        this.id = projection.getId();
        this.name = projection.getName();
        this.value = projection.getValue();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

}
