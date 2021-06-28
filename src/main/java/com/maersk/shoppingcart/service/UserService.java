package com.maersk.shoppingcart.service;

import com.maersk.shoppingcart.domain.AddUser;
import com.maersk.shoppingcart.domain.UserPrincipal;
import com.maersk.shoppingcart.entity.CartEntity;
import com.maersk.shoppingcart.entity.UserEntity;
import com.maersk.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartService cartService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserPrincipal(user);
    }

    public void addUser(AddUser addUser) {
        UserEntity user = new UserEntity();
        user.setName(user.getName());
        user.setPassword(user.getPassword());
        user.setRole("USER");
        user.setUsername(user.getUsername());
        user = userRepository.save(user);

        cartService.createCart(user.getId());
    }
}
