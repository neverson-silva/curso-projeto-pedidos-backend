package com.neversonsilva.cursomc.services;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

public class MockMailService extends AbstractMailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockMailService.class) ;
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
		
	}

}
