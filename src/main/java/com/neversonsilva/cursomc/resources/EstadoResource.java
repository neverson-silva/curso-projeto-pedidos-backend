package com.neversonsilva.cursomc.resources;

import com.neversonsilva.cursomc.domains.Cidade;
import com.neversonsilva.cursomc.domains.Estado;
import com.neversonsilva.cursomc.dto.CidadeDTO;
import com.neversonsilva.cursomc.dto.EstadoDTO;
import com.neversonsilva.cursomc.services.CidadeService;
import com.neversonsilva.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping()
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> estados = estadoService.findAll();

        List<EstadoDTO> estadoDTOS = estados.stream().map(EstadoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(estadoDTOS);
    }

    @GetMapping("/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable("estadoId") Integer estadoId) {
        List<Cidade> cidades = cidadeService.findByEstadoId(estadoId);

        List<CidadeDTO> cidadeDTOS = cidades.stream().map(CidadeDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(cidadeDTOS);
    }
}
