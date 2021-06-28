package com.maersk.shoppingcart.utils;

import com.maersk.shoppingcart.domain.AddItem;

import java.util.UUID;

public class TestUtils {

    public static AddItem getAddItem(UUID productId) {
        AddItem addItem = new AddItem();
        addItem.setProductId(productId.toString());
        addItem.setQuantity(2);
        return addItem;
    }
}
