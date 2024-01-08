package com.training.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    private int customerId;

    private int productId;

    private int quantity;

}
