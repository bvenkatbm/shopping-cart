package com.maersk.shoppingcart.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Product {

    private String name;

    private Integer quantity;
}
