package com.neversonsilva.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.neversonsilva.cursomc.domains.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
