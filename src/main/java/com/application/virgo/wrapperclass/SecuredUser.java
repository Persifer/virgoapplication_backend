package com.application.virgo.wrapperclass;

import com.application.virgo.model.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class che racchiude le informazioni di un utente in una variabile UserDetails
 */
public class SecuredUser implements UserDetails {


    private final Utente utenteInformation;
    private final List<SecuredRoles> authorities;;

    public SecuredUser(Utente utenteInformation) {
        this.utenteInformation = utenteInformation;
        this.authorities = utenteInformation.getUserRole()
                .stream()
                .map(SecuredRoles::new)
                .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return this.utenteInformation.getIsAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.utenteInformation.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.utenteInformation.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.utenteInformation.getIsEnabled();
    }
}
