package com.application.virgo.utilities;

import com.application.virgo.model.Utente;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
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

    /**
     * Preleva la prima immagine di un immobile
     * @return una stringa con il path dell'immagine
     */
    public static String getSingleImageFromList(String listaImmagini){
        String image = "";

        if(listaImmagini== null ||
                listaImmagini.isBlank() || listaImmagini.isEmpty()){
            image = "http://localhost:8080/no_image.jpg";
        }else {
            image = List.of(listaImmagini.split("\\|")).get(0);
        }


       return image;
    }

    public static List<String> getListaImamginiFromString(String listaImmagini){
        List<String> image;

        if(listaImmagini== null ||
                listaImmagini.isBlank() || listaImmagini.isEmpty()){
            image = List.of("http://localhost:8080/no_image.jpg");
        }else {
            image = List.of(listaImmagini.split("~"));
        }

        return image;
    }

}
