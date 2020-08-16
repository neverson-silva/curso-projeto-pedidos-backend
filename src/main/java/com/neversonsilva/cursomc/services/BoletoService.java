package com.neversonsilva.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import com.neversonsilva.cursomc.domains.PagamentoComBoleto;

import org.springframework.stereotype.Service;

@Service
public class BoletoService {
    
    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, 
                        Date instantePedido) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(instantePedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDataVencimento(cal.getTime());
        
    }
}