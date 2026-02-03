package com.foodhub.backend.execption;

import com.foodhub.backend.response.APIErrorResponse;
import com.foodhub.backend.response.APIErrorType;
import com.foodhub.backend.response.APIResponder;
import com.foodhub.backend.response.APIResponseKeyError;
import com.foodhub.backend.response.APIResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final APIResponder apiResponder;

	public GlobalExceptionHandler(APIResponder apiResponder) {
		this.apiResponder = apiResponder;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<APIErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<APIResponseKeyError> errors = new ArrayList<>();
		ex.getBindingResult()
			.getFieldErrors()
			.forEach(fieldError -> errors
				.add(new APIResponseKeyError(fieldError.getDefaultMessage(), APIErrorType.VALIDATION_ERROR)));
		return apiResponder.respondWithError(new APIErrorResponse(errors, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<APIErrorResponse> handleRuntime(RuntimeException ex) {
		return apiResponder.respondWithError(ex, APIResponseType.ERROR, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<APIErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
		return apiResponder.respondWithError(exception, APIResponseType.ERROR, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<APIErrorResponse> handleIllegalStateException(IllegalStateException exception) {
		return apiResponder.respondWithError(exception, APIResponseType.ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
