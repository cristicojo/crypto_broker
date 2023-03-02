package crypto.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("message", ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors()
				.forEach(error -> {
					String fieldName = ((FieldError) error).getField();
					String message = error.getDefaultMessage();
					errors.put(fieldName, message);
				});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidFormatException.class)
	public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getPath().forEach(error -> {
			String fieldName = error.getFieldName();
			String message = "please enter only numbers";
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
