package com.training.repository;

import com.training.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> findProductByIds(List<Integer> ids);

    boolean existsByProductCode(String productCode);

}
