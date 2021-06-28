package com.maersk.shoppingcart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "item")
@Data
public class ItemEntity {

    @Id
    @Column(columnDefinition = "uuid")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id", columnDefinition = "uuid")
    private ProductEntity productEntity;

    @Transient
    private CartEntity cartEntity;

    private Integer requiredQuantity = 0;
}
