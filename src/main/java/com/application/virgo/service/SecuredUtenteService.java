package com.application.virgo.service;

import com.application.virgo.model.SecuredUser;
import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.interfaces.UtenteService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecuredUtenteService implements UserDetailsService {


    private final UtenteJpaRepository utenteRepo;

    public SecuredUtenteService(UtenteJpaRepository userRepository) {
        this.utenteRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utenteRepo
                .findByEmail(username)
                .map(SecuredUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

}
