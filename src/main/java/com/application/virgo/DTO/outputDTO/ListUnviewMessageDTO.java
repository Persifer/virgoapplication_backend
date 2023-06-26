package com.application.virgo.DTO.outputDTO;

import lombok.*;
/**
 * Dati lista messaggi non visualizzati in output dalla business logic
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListUnviewMessageDTO {

    private String email;
    private Integer unviewedEmail;
}
