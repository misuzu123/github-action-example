package com.training.repository;

import com.training.entity.Cart;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("SELECT c FROM Cart c WHERE c.customer.id = :id")
    List<Cart> findByCustomerId(Integer id);

    @Query("SELECT (COUNT(c.id) > 0) FROM Cart c WHERE c.customer.id = :customerId AND c.product.id = :productId")
    boolean existsByCustomerIdAndProductId(Integer customerId, Integer productId);

    @Query("SELECT c.id  FROM Cart c WHERE c.customer.id = :customerId AND c.product.id = :productId")
    Cart getCartByCustomerAndProduct(Integer customerId, Integer productId);

    @Query("SELECT c FROM Cart c WHERE c.id IN :cartIds")
    List<Cart> findCartsByIds(List<UUID> cartIds);

}
