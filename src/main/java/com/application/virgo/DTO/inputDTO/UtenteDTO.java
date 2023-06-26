package com.application.virgo.DTO.inputDTO;

import com.application.virgo.model.Immobile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Dati Utente in input dal front-end
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UtenteDTO {

    @NotNull
    @Pattern(regexp = "[A-z]")
    private String nome;
    @NotNull
    @Pattern(regexp = "[A-z]")
    private String cognome;
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@+[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d#@$!%*?&]{6,16}$")
    private String password;


    private LocalDate dataNascita;


    public UtenteDTO(String nome, String cognome, String email, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataNascita = dataNascita;
    }
}
