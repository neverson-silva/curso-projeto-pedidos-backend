package com.neversonsilva.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.dto.ClienteDTO;
import com.neversonsilva.cursomc.exceptions.FieldMessage;
import com.neversonsilva.cursomc.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;


public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    
    @Autowired
    private ClienteRepository repo;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(ClienteUpdate ann) {
    }
    
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
    
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String> ) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var urlId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();        
    

        Optional<Cliente> clienteAux = repo.findByEmail(objDto.getEmail());

        if (clienteAux.isPresent() && !clienteAux.get().getId().equals(urlId) ) {
            list.add(new FieldMessage("email", "Email já existente."));
        }


        list.forEach((FieldMessage e) -> {
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(e.getMessage())
                .addPropertyNode(e.getFieldName()).addConstraintViolation();
        });
   

        return list.isEmpty();
    }
}