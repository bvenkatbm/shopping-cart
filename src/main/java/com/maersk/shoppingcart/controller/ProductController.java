package com.maersk.shoppingcart.controller;

import com.maersk.shoppingcart.entity.ProductEntity;
import com.maersk.shoppingcart.service.ProductService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value = "Product API", tags = {"Product"})
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(
            value = "View product Catalogue",
            notes = "API returns all the available products",
            response = ProductEntity.class,
            responseContainer = "Page",
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved products"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Products to display")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(path = "/v1/product-catalogue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductEntity>> viewProducts(Pageable pageable) {
        Page<ProductEntity> productEntities = productService.getProducts(pageable);

        if (!productEntities.hasContent())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(productEntities);
    }
}
