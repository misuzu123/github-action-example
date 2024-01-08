package com.training.service.cart;

import com.training.common.constant.Errors;
import com.training.common.utils.DateTimeUtils;
import com.training.dto.cart.CartDto;
import com.training.dto.cart.CartRequest;
import com.training.entity.Cart;
import com.training.entity.Customer;
import com.training.entity.Product;
import com.training.exception.BadRequestException;
import com.training.exception.ErrorParam;
import com.training.exception.SysError;
import com.training.repository.CartRepository;
import com.training.service.customer.CustomerService;
import com.training.service.product.ProductService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CustomerService customerService,
            ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public List<CartDto> getCartByCustomer(Integer customerId) {
        List<Cart> carts = cartRepository.findByCustomerId(customerId);

        return carts.stream()
                .map(this::convertDto)
                .toList();
    }

    @Override
    public CartDto getCartInfo(UUID cartId) {
        Cart cart = findCartById(cartId);

        return convertDto(cart);
    }

    @Override
    @Transactional
    public void addToCart(CartRequest request) {
        Cart cart = new Cart();
        if (checkExistCartItem(request.getCustomerId(), request.getProductId())) {
            cart = cartRepository.getCartByCustomerAndProduct(request.getCustomerId(),
                    request.getProductId());
            int quantity = cart.getQuantity() + request.getQuantity();
            cart.setQuantity(quantity);
        } else {
            Customer customer = customerService.findCustomerById(request.getCustomerId());
            Product product = productService.findProductById(request.getProductId());

            cart.setCustomer(customer);
            cart.setProduct(product);
            cart.setQuantity(request.getQuantity());
            cart.setDateTime(LocalDateTime.now());
            cart.setPayment(false);
        }

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeCartItem(List<UUID> cartIds) {
        List<Cart> carts = cartRepository.findCartsByIds(cartIds);
        cartRepository.deleteAll(carts);
    }

    @Override
    @Transactional
    public void editCartItem(UUID cartId, Integer quantity) {
        Cart cart = findCartById(cartId);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void payItem(UUID cartId) {
        Cart cart = findCartById(cartId);
        cart.setPayment(true);
        cartRepository.save(cart);
    }

    private Cart findCartById(UUID cartId) {

        return cartRepository.findById(cartId).orElseThrow(
                () -> new BadRequestException(new SysError(Errors.ERROR_PRODUCT_CART_EXIST, new ErrorParam(Errors.CART)))
        );
    }

    private boolean checkExistCartItem(Integer customerId, Integer productId) {
        return cartRepository.existsByCustomerIdAndProductId(customerId, productId);
    }

    private CartDto convertDto(Cart cart) {
        Product product = cart.getProduct();

        return CartDto.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .productId(product.getId())
                .productName(product.getName())
                .quantity(cart.getQuantity())
                .dateTime(DateTimeUtils.formatterDateTime(cart.getDateTime()))
                .isPayment(cart.isPayment())
                .build();
    }

}
