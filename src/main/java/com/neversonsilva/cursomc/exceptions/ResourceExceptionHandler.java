package com.neversonsilva.cursomc.exceptions;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError error = newErrorInstance(StandardError.class,
				HttpStatus.NOT_FOUND.value(), "Não encontrado",	e, request);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		StandardError error = newErrorInstance(StandardError.class,
				HttpStatus.BAD_REQUEST.value(), "Integridade de dados",	e, request);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError error = newErrorInstance(ValidationError.class,
				HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e, request);

		e.getBindingResult().getFieldErrors().forEach((FieldError fieldError) -> {
			error.addError(fieldError.getField(), fieldError.getDefaultMessage());
		});

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {

		StandardError error = newErrorInstance(StandardError.class,
				HttpStatus.FORBIDDEN.value(), "Acesso negado",	e, request);

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {

		StandardError error = newErrorInstance(StandardError.class,
				HttpStatus.BAD_REQUEST.value(), "Erro de arquivo",	e, request);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {

		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandardError error = newErrorInstance(StandardError.class,
				code.value(), "Erro Amazon Service",	e, request);
		return ResponseEntity.status(code).body(error);
	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {

		StandardError error = newErrorInstance(StandardError.class,
				HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client",	e, request);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
		StandardError error = newErrorInstance(StandardError.class,
				HttpStatus.BAD_REQUEST.value(), "Erro S3",	e, request);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	private <T extends StandardError> T newErrorInstance(Class<? extends StandardError> classe,
														 int statusCode,
														 String error,
														 Throwable exception,
														 HttpServletRequest req) {

		T newClasse = null;
		try {
			newClasse = (T) classe.newInstance();
			newClasse.setTimestamp(System.currentTimeMillis());
			newClasse.setStatus(statusCode);
			newClasse.setError(error);
			newClasse.setMessage(exception.getMessage());
			newClasse.setPath(req.getRequestURI());
			return newClasse;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
