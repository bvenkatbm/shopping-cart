package com.maersk.shoppingcart.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddUser {

    private String name;
    private String username;
    private String password;
}
