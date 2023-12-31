package me.pedrokaua.securityproject.models.repositories;

import me.pedrokaua.securityproject.models.entities.ProductModel;
import me.pedrokaua.securityproject.models.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    @Query(nativeQuery = true, value = """
        SELECT LOWER(CONCAT(
                   SUBSTR(HEX(p.id), 1, 8), '-',
                   SUBSTR(HEX(p.id), 9, 4), '-',
                   SUBSTR(HEX(p.id), 13, 4), '-',
                   SUBSTR(HEX(p.id), 17, 4), '-',
                   SUBSTR(HEX(p.id), 21)
                 )) AS id, p.name, p.value FROM tb_products p
    """)
    public List<ProductProjection> findAllString();

    public Optional<ProductModel> deleteByName(String name);
}
