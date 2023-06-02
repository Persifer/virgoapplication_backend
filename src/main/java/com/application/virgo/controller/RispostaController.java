package com.application.virgo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/site/immobile")
@Validated
@AllArgsConstructor
public class RispostaController {

    @PostMapping("/reply/{id_immobile}/{id_domanda}")
    public ResponseEntity<String> replyToDomanda(){
        return new ResponseEntity<>("", HttpStatus.OK);
  }


}
