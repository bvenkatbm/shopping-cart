package com.maersk.shoppingcart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@ApiModel(description = "Model to add product to cart")
public class AddItem {

    @JsonIgnore
    private UUID userId;

    @ApiModelProperty(notes = "Id of the product viewed in product Catalogue", example = "3cd9343d-e3de-41eb-8031-2aae8ba2e802")
    private String productId;

    @ApiModelProperty(notes = "Quantity to add")
    private Integer quantity = 0;
}
