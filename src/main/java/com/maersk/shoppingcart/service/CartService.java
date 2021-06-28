package com.maersk.shoppingcart.service;

import com.maersk.shoppingcart.domain.*;
import com.maersk.shoppingcart.entity.CartEntity;
import com.maersk.shoppingcart.entity.ItemEntity;
import com.maersk.shoppingcart.entity.ProductEntity;
import com.maersk.shoppingcart.exception.ItemNotFoundInCartException;
import com.maersk.shoppingcart.exception.OutOfQuantityException;
import com.maersk.shoppingcart.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    public Cart addProduct(AddItem addItem) {

        // validate product
        ProductEntity productEntity = productService.validateProduct(addItem.getProductId());
        if (addItem.getQuantity() > productEntity.getAvailableQuantity())
            throw new OutOfQuantityException("Cart value exceeds the available value, please add quantities between 1 to " + productEntity.getAvailableQuantity());

        if (addItem.getQuantity() <= 0)
            throw new OutOfQuantityException("Request quantity is not valid, please add quantities between 1 to " + productEntity.getAvailableQuantity());

        Optional<CartEntity> optionalCart = cartRepository.findByUserId(addItem.getUserId());
        CartEntity userCart = null;
        if (optionalCart.isPresent()) {
            userCart = optionalCart.get();

            ItemEntity itemEntity = new ItemEntity();

            for (ItemEntity itemEntityDb : userCart.getItems()) {
                if (itemEntityDb.getProductEntity().getId().equals(productEntity.getId())) {
                    itemEntity = itemEntityDb;
                    break;
                }
            }
            if (itemEntity.getRequiredQuantity() + addItem.getQuantity() > productEntity.getAvailableQuantity())
                throw new OutOfQuantityException("Cart value exceeds the available value, please add quantities between 1 to "
                        + productEntity.getAvailableQuantity());
            itemEntity.setProductEntity(productEntity);
            itemEntity.setRequiredQuantity(itemEntity.getRequiredQuantity() + addItem.getQuantity());

            userCart.getItems().add(itemEntity);
            userCart = cartRepository.save(userCart);

        } else {
            // ideally for every user created we should create a cart.
            // always cart will be present and items in a cart should be empty.
            log.error("Cart is not available, Please check");
            // we should throw a custom Internal Server Exception and cant add products until resolved

            // another way to do is to add new Cart. But it all depends on microservices architecture
        }

        return mapCart(userCart);
    }

    public Cart editCart(UpdateProduct updateProduct) {
        // validate product
        ProductEntity productEntity = productService.validateProduct(updateProduct.getProductId());

        if (updateProduct.getQuantity() <= 0)
            throw new OutOfQuantityException("Please select a proper quantity");

        CartEntity userCart = cartRepository.findByUserId(updateProduct.getUserId()).orElse(new CartEntity());

        ItemEntity itemEntity = userCart.getItems().stream()
                .filter(cartItems -> cartItems.getProductEntity().getId().equals(productEntity.getId()))
                .findFirst().orElseThrow(() -> new ItemNotFoundInCartException("Product " + productEntity.getName() + " is not present in the cart to update"));

        if (updateProduct.getQuantity() == 0)
            userCart.getItems().remove(itemEntity);
        else
            itemEntity.setRequiredQuantity(updateProduct.getQuantity());

        userCart = cartRepository.save(userCart);

        return mapCart(userCart);
    }

    public Cart deleteProductFromCart(String product) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UpdateProduct updateProduct = new UpdateProduct(product);
        updateProduct.setUserId(user.getUser().getId());
        return editCart(updateProduct);
    }

    private Cart mapCart(CartEntity userCart) {
        Cart cart = new Cart();
        List<CartItem> cartItemList = new ArrayList<>();
        final float[] cartPrice = {0f};
        userCart.getItems().forEach(itemEntity -> {
            CartItem cartItem = new CartItem();

            ProductEntity pe = itemEntity.getProductEntity();
            cartItem.setName(pe.getName());
            int itemQuantity = itemEntity.getRequiredQuantity();
            cartItem.setQuantity(itemQuantity);
            float itemCost = itemQuantity * pe.getCost();
            cartItem.setItemCost(itemCost);

            cartItemList.add(cartItem);
            cartPrice[0] += itemCost;
        });

        cart.setCartItems(cartItemList);
        cart.setCartValue(cartPrice[0]);
        return cart;
    }

    public Cart getCart() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cartRepository.findByUserId(user.getUser().getId()).map(this::mapCart).orElseGet(Cart::new);
    }

    public void createCart(UUID id) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserId(id);
        cartRepository.save(cartEntity);
    }
}
