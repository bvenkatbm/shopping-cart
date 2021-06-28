package com.maersk.shoppingcart.config;

import com.maersk.shoppingcart.entity.CartEntity;
import com.maersk.shoppingcart.entity.ProductEntity;
import com.maersk.shoppingcart.entity.UserEntity;
import com.maersk.shoppingcart.repository.CartRepository;
import com.maersk.shoppingcart.repository.ProductRepository;
import com.maersk.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DatabaseConfig {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void setup() {
        ProductEntity entity = new ProductEntity();
        entity.setName("Pencil");
        entity.setAvailableQuantity(20);
        entity.setCost(5f);
        productRepository.save(entity);

        entity = new ProductEntity();
        entity.setName("Pen");
        entity.setAvailableQuantity(20);
        entity.setCost(25f);
        productRepository.save(entity);

        UserEntity user = new UserEntity();
        user.setName("Venkat");
        user.setPassword("complexPassword");
        user.setRole("USER");
        user.setUsername("venben@fmail.com");
        user = userRepository.save(user);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserId(user.getId());
        cartRepository.save(cartEntity);

        user = new UserEntity();
        user.setName("Admin");
        user.setPassword("veryComplexPassword");
        user.setRole("ADMIN");
        user.setUsername("admin@maersk.com");
        user = userRepository.save(user);
        cartEntity = new CartEntity();
        cartEntity.setUserId(user.getId());
        cartRepository.save(cartEntity);
    }
}
