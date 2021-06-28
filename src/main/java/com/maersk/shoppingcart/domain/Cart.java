package com.maersk.shoppingcart.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Cart {

    private Float cartValue = 0f;

    private List<CartItem> cartItems;

    //private Page<CartItem> cartItems;
}
