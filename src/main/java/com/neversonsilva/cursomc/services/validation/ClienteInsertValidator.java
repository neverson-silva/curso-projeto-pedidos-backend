package com.neversonsilva.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.neversonsilva.cursomc.domains.enums.TipoCliente;
import com.neversonsilva.cursomc.dto.ClienteNewDto;
import com.neversonsilva.cursomc.exceptions.FieldMessage;
import com.neversonsilva.cursomc.services.validation.utils.BR;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {
    
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


        list.forEach((FieldMessage e) -> {
            context.disableDefaultConstraintViolation();
            
            context.buildConstraintViolationWithTemplate(e.getMessage())
                .addPropertyNode(e.getFieldName()).addConstraintViolation();
        });
   

        return list.isEmpty();
    }
}