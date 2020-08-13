package com.neversonsilva.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.neversonsilva.cursomc.domains.Cliente;
import com.neversonsilva.cursomc.domains.enums.TipoCliente;
import com.neversonsilva.cursomc.dto.ClienteNewDto;
import com.neversonsilva.cursomc.exceptions.FieldMessage;
import com.neversonsilva.cursomc.repositories.ClienteRepository;
import com.neversonsilva.cursomc.services.validation.utils.BR;

import org.springframework.beans.factory.annotation.Autowired;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {
    
    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }
    
    @Override
    public boolean isValid(ClienteNewDto objDto, ConstraintValidatorContext context) {
    
        List<FieldMessage> list = new ArrayList<>();
    
        boolean isCPF = objDto.getTipo() == TipoCliente.PESSOA_FISICA.getCode();

        if (isCPF && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (!isCPF && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Optional<Cliente> clienteAux = repo.findByEmail(objDto.getEmail());

        if (clienteAux.isPresent() ) {
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