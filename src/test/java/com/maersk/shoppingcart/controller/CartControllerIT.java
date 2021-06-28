package com.maersk.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maersk.shoppingcart.domain.AddItem;
import com.maersk.shoppingcart.repository.ProductRepository;
import com.maersk.shoppingcart.service.UserService;
import com.maersk.shoppingcart.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class CartControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Mock
    UserService userService;

    final String serviceURL = "http://localhost:8002";

    @Autowired
    ProductRepository productRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithUserDetails(value = "venben@fmail.com")
    public void add_item_to_cart_test() throws Exception {

        AddItem request = TestUtils.getAddItem(productRepository.findAll().get(0).getId());

        mockMvc.perform(post("/v1/cart/product/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.cartItems", hasSize(1)));

    }
}
