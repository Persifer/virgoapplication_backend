package com.application.virgo.utilities;

import com.application.virgo.model.Utente;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class Util {

    public static UserDetails convertBy(Utente u) {
        if (u != null) {
            return new org.springframework.security.core.userdetails.User(u.getEmail()
                    , u.getPassword(),
                    u.getUserRole().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getRuolo()))
                            .collect(Collectors.toList()));
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

}
