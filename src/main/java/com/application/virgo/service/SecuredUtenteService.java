package com.application.virgo.service;

import com.application.virgo.security.SecuredUser;
import com.application.virgo.repositories.UtenteJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SecuredUtenteService implements UserDetailsService {


    private final UtenteJpaRepository utenteRepo;

    public SecuredUtenteService(UtenteJpaRepository userRepository) {
        this.utenteRepo = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utenteRepo
                .findByEmail(username)
                .map(SecuredUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }



}
