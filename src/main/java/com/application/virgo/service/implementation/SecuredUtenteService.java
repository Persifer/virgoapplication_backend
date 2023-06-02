package com.application.virgo.service.implementation;

import com.application.virgo.model.Utente;
import com.application.virgo.utilities.Util;
import com.application.virgo.wrapperclass.SecuredUser;
import com.application.virgo.repositories.UtenteJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecuredUtenteService implements UserDetailsService {


    private final UtenteJpaRepository utenteRepo;

    public SecuredUtenteService(UtenteJpaRepository userRepository) {
        this.utenteRepo = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente u= utenteRepo
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
        return Util.convertBy(u);
    }



}
