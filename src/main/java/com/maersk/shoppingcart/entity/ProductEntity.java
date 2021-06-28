package com.maersk.shoppingcart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Currency;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "product")
public class ProductEntity {

    @Id
    @Column(columnDefinition = "uuid")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    private UUID id;

    private String name;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    //todo: convert to currency/ Big Decimal
    private Float cost;

    //todo: extend using audit log

}
