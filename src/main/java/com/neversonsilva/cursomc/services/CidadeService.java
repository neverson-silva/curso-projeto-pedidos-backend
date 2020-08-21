package com.neversonsilva.cursomc.services;

import com.neversonsilva.cursomc.domains.Cidade;
import com.neversonsilva.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findByEstadoId(Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }
}
