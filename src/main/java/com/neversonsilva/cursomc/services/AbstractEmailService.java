package com.neversonsilva.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.neversonsilva.cursomc.domains.Pedido;

import lombok.Getter;

public abstract class AbstractEmailService implements EmailService{

	
	@Value("${default.sender}")
	@Getter
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(sm);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		
		MimeMessage mm;
		try {
			mm = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		} 
				
	}

	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeHelper = new MimeMessageHelper(mm, true);
		
		mimeHelper.setTo(pedido.getCliente().getEmail());
		mimeHelper.setFrom(sender);
		mimeHelper.setSubject("Pedido confirmado! Código: " + pedido.getId());
		mimeHelper.setSentDate(new Date(System.currentTimeMillis()));
		
		mimeHelper.setText(htmlFromTemplatePedido(pedido), true);
		
		return mm;
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + pedido.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		
		sm.setText( pedido.toString() );
		return sm;
	}
	
	
	protected String htmlFromTemplatePedido(Pedido pedido) {
	
		Context context = new Context();
		
		context.setVariable("pedido", pedido);
		
		return templateEngine.process("email/confirmacaoPedido", context);
		
	}
	
	

}
