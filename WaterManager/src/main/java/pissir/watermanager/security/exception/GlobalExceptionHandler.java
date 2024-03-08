package pissir.watermanager.security.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Getter
@Setter
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(JwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorMessage handleJwtException(JwtException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Token non valido o scaduto.");
	}
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleEntityNotFoundException(EntityNotFoundException exception) {
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		// Qui puoi estrarre i dettagli degli errori di validazione e formattarli
		return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Errore di validazione dei dati.");
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorMessage handleAccessDeniedException(AccessDeniedException exception) {
		return new ErrorMessage(HttpStatus.FORBIDDEN.value(), "Accesso negato.");
	}
	
	// Qui puoi aggiungere altri gestori di eccezioni, se necessario

	@Getter
	@Setter
	public static class ErrorMessage {
		
		private int statusCode;
		private String message;
		
		
		public ErrorMessage(int statusCode, String message) {
			this.statusCode = statusCode;
			this.message = message;
		}
		
		// Getter e Setter
	}
	
}
