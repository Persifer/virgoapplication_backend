package com.application.virgo.DTO.outputDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListUnviewMessageDTO {

    private String email;
    private Integer unviewedEmail;
}
