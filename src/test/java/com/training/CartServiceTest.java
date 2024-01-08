package com.training;

import com.training.dto.cart.CartDto;
import com.training.entity.Cart;
import com.training.entity.Customer;
import com.training.entity.Product;
import com.training.repository.CartRepository;
import com.training.service.cart.CartService;
import com.training.service.cart.CartServiceImpl;
import com.training.service.customer.CustomerService;
import com.training.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductService productService;

    @Mock
    private CartService cartService;


    @BeforeEach
    public void setUp() {
        cartService = new CartServiceImpl(cartRepository, customerService, productService);
    }

    @Test
    void whenGetCartByCustomer_thenReturn() {
        int customerId = 1;

        List<Cart> sampleCarts = createCarts();

        when(cartRepository.findByCustomerId(customerId)).thenReturn(sampleCarts);

        List<CartDto> result = cartService.getCartByCustomer(customerId);

        assertEquals(sampleCarts.size(), result.size());

        verify(cartRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void whenGetCartById_thenReturn() {
        UUID cartId = UUID.fromString("7cb71d64-c473-45c1-9326-99a189a6f8f4");

        Cart sampleCart = createCarts().getFirst();
        Optional<Cart> optionalCart = Optional.of(sampleCart);
        when(cartRepository.findById(cartId)).thenReturn(optionalCart);

        CartDto result = cartService.getCartInfo(cartId);
        assertNotNull(result);
        assertEquals(cartId, result.getId());

        verify(cartRepository, times(1)).findById(cartId);
    }

    private List<Cart> createCarts() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFullName("Dương Đẹp Trai");
        customer.setAge(24);
        customer.setGender("Male");

        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Bịch sà bông");
        product1.setProductCode("BCB");
        product1.setPrice(123);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Bịch bông gòn");
        product2.setProductCode("BBG");
        product2.setPrice(121);

        Cart cart1 = new Cart(UUID.fromString("7cb71d64-c473-45c1-9326-99a189a6f8f4"), customer, product1, 2, LocalDateTime.now(), false);
        Cart cart2 = new Cart(UUID.fromString("48405334-ae73-49d2-bb69-1c2893c23b28"), customer, product2, 1, LocalDateTime.now(), true);
        return Arrays.asList(cart1, cart2);
    }

}
