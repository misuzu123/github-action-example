package com.training.controller;

import com.training.common.constant.Constant;
import com.training.dto.ResponseJson;
import com.training.dto.cart.CartDto;
import com.training.dto.cart.CartRequest;
import com.training.service.cart.CartService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ResponseJson<List<CartDto>>> getCartByCustomer(@PathVariable("customerId") Integer customerId) {
        List<CartDto> carts = cartService.getCartByCustomer(customerId);
        return ResponseEntity.ok().body(new ResponseJson<>(carts, HttpStatus.OK, Constant.SUCCESS));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ResponseJson<CartDto>> getCartInfo(@PathVariable("cartId") UUID cartId) {
        CartDto cart = cartService.getCartInfo(cartId);
        return ResponseEntity.ok().body(new ResponseJson<>(cart, HttpStatus.OK, Constant.SUCCESS));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseJson<Boolean>> addToCart(@RequestBody CartRequest request) {
        cartService.addToCart(request);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<ResponseJson<Boolean>> editCartItem(@PathVariable("cartId") UUID cartId, @RequestParam Integer quantity) {
        cartService.editCartItem(cartId, quantity);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ResponseJson<Boolean>> removeCartItem(@RequestBody List<UUID> cartIds) {
        cartService.removeCartItem(cartIds);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

    @GetMapping("/payment")
    public ResponseEntity<ResponseJson<Boolean>> payItem(@RequestParam UUID cartId) {
        cartService.payItem(cartId);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

}
