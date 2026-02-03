package com.foodhub.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class APIResponseKeyError {

	protected String message;

	protected APIErrorType errorType;

	public APIResponseKeyError() {
		this(null, APIErrorType.GENERIC_ERROR);
	}

	public APIResponseKeyError(Throwable exception) {
		this(exception.getMessage(), APIErrorType.GENERIC_ERROR);
	}

}
