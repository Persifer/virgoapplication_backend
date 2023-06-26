package com.application.virgo.service.interfaces;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.PreventivoException;

public interface CalcoloPreventiviService {

    public Double calcolaPreventivoImmobile(Long idContratto, Integer selettoreAzienda) throws ContrattoException, PreventivoException;
}
