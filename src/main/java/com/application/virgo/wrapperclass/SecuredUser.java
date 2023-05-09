package com.application.virgo.wrapperclass;

import com.application.virgo.model.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class SecuredUser implements UserDetails {


    private final Utente utenteInformation;

    public SecuredUser(Utente utenteInformation) {
        this.utenteInformation = utenteInformation;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Arrays.stream(utenteInformation
//                        .getUserRoles()
//                        .split("|"))
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return utenteInformation.getUserRole()
                .stream()
                .map(SecuredRuoles::new)
                .collect(Collectors.toList());
    }

    public Utente getUtenteInformation() {
        return utenteInformation;
    }

    @Override
    public String getPassword() {
        return this.utenteInformation.getPassword();
    }

    @Override
    public String getUsername() {
        return this.utenteInformation.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
