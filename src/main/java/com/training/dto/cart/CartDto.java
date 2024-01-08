package com.training.dto.cart;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private UUID id;

    private int customerId;

    private int productId;

    private String productName;

    private int quantity;

    private String dateTime;

    private boolean isPayment;

}
