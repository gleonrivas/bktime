package com.gleon.bktime.services;


import com.gleon.bktime.models.User;
import com.gleon.bktime.models.UserType;
import com.gleon.bktime.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){

        User newUser = new User();

        try {

            newUser.setUserName(user.getUserName());
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setPhone(user.getPhone());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));

            if (user.getId() != null) newUser.setId(user.getId());

            if (user.getUserType() != null){
                newUser.setUserType(user.getUserType());
            } else {
                newUser.setUserType(UserType.user);
            }

            if (user.getAccountState() == null) {
                newUser.setAccountState(1);
            } else {
                newUser.setAccountState(user.getAccountState());
            }



        }catch (Exception e){
            System.out.println(e);
        }

        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.verifyUsernameOrEmail(username);
        GrantedAuthority rol = new SimpleGrantedAuthority(user.getUserType().toString());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(rol));
        return userDetails;
    }
}
