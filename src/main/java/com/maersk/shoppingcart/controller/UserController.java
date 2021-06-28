package com.maersk.shoppingcart.controller;

import com.maersk.shoppingcart.domain.AddUser;
import com.maersk.shoppingcart.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Slf4j
@Api(value = "User Management API", tags = {"User"})
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(
            value = "Add users to Shopping cart platform",
            notes = "API adds users",
            response = String.class,
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created user"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/v1/user/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUsers(@RequestBody AddUser addUser) {
        userService.addUser(addUser);
        return ResponseEntity.created(URI.create("/v1/user/add")).body("Created User");
    }
}
