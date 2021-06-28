package com.maersk.shoppingcart.controller;

import com.maersk.shoppingcart.domain.*;
import com.maersk.shoppingcart.service.CartService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(value = "Cart Management API", tags = {"Cart"})
public class CartController {

    @Autowired
    CartService cartService;

    @ApiOperation(
            value = "Add product to the cart",
            notes = "API adds product to Cart and displays cart",
            response = Cart.class,
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added Products to Cart"),
            @ApiResponse(code = 400, message = "Bad Request, check message!"),
            @ApiResponse(code = 204, message = "No content")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping(path = "/v1/cart/product/add")
    public ResponseEntity<Cart> addToCart(@RequestBody AddItem addItem) {
        log.info("Add Product {} to Cart", addItem.getProductId());
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        addItem.setUserId(user.getUser().getId());
        return addResponseMetaData(cartService.addProduct(addItem));
    }

    @ApiOperation(
            value = "Update product details on the cart",
            notes = "API updates product information to Cart and displays cart",
            response = Cart.class,
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated product information to cart"),
            @ApiResponse(code = 400, message = "Bad Request, check message!"),
            @ApiResponse(code = 204, message = "Successfully updated product from cart and cart is empty")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping(path = "/v1/cart/product/update")
    public ResponseEntity<Cart> updateCart(@RequestBody UpdateProduct updateProduct) {
        log.info("Update Product {} to Cart", updateProduct.getProductId());
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        updateProduct.setUserId(user.getUser().getId());
        return addResponseMetaData(cartService.editCart(updateProduct));
    }

    @ApiOperation(
            value = "Remove product from the cart",
            notes = "API removes product from Cart and displays cart",
            response = Cart.class,
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed product from cart"),
            @ApiResponse(code = 400, message = "Bad Request, check message!"),
            @ApiResponse(code = 204, message = "Successfully removed product from cart and cart is empty")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @DeleteMapping(path = "/v1/cart/remove/product/{product}")
    public ResponseEntity<Cart> updateCart(@PathVariable String product) {
        log.info("Remove Product {} from Cart", product);
        return addResponseMetaData(cartService.deleteProductFromCart(product));
    }

    @ApiOperation(
            value = "View products of the cart",
            notes = "API to display cart",
            response = Cart.class,
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved cart"),
            @ApiResponse(code = 400, message = "Bad Request, check message!"),
            @ApiResponse(code = 204, message = "Cart is empty")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(path = "/v1/cart/view")
    public ResponseEntity<Cart> viewCart() {
        log.info("View cart");
        return addResponseMetaData(cartService.getCart());
    }

    private ResponseEntity<Cart> addResponseMetaData(Cart cart) {
        if (cart.getCartItems().isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(cart);
    }
}
