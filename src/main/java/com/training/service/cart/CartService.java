package com.training.service.cart;

import com.training.dto.cart.CartDto;
import com.training.dto.cart.CartRequest;
import java.util.List;
import java.util.UUID;

public interface CartService {

    List<CartDto> getCartByCustomer(Integer customerId);

    CartDto getCartInfo(UUID cartId);

    void addToCart(CartRequest request);

    void editCartItem(UUID cartId, Integer quantity);

    void removeCartItem(List<UUID> cartIds);

    void payItem(UUID cartId);

}
