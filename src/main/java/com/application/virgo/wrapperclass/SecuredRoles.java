package com.application.virgo.wrapperclass;

import com.application.virgo.model.Ruolo;
import org.springframework.security.core.GrantedAuthority;


public class SecuredRoles implements GrantedAuthority {

    private final Ruolo ruolo;

    public SecuredRoles(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String getAuthority() {
        return ruolo.getRuolo();
    }
}
