package com.foodhub.backend.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class APIResponder implements IAPIResponder {

	@Override
	public <T> ResponseEntity<APINormalResponse<T>> respond(APINormalResponse<T> response) {
		return new ResponseEntity<>(response, response.getMeta().getStatus());
	}

	@Override
	public <T> ResponseEntity<APINormalResponse<T>> respond(T data) {
		APIResponseKeyMeta apiResponseKeyMeta = new APIResponseKeyMeta(APIResponseType.SUCCESS, HttpStatus.OK);
		APINormalResponse<T> apiNormalResponse = new APINormalResponse<>(data);
		return new ResponseEntity<>(apiNormalResponse, apiResponseKeyMeta.getStatus());
	}

	@Override
	public <T> ResponseEntity<APINormalResponse<T>> respond(T data, String message) {
		APIResponseKeyMeta apiResponseKeyMeta = new APIResponseKeyMeta(APIResponseType.SUCCESS, HttpStatus.OK);
		APINormalResponse<T> apiNormalResponse = new APINormalResponse<>(data, message);
		return new ResponseEntity<>(apiNormalResponse, apiResponseKeyMeta.getStatus());
	}

	@Override
	public <T> ResponseEntity<APINormalResponse<T>> respond(T data, String message, APIResponseType responseType,
			HttpStatus status) {
		APIResponseKeyMeta apiResponseKeyMeta = new APIResponseKeyMeta(message, responseType, status);
		APINormalResponse<T> apiNormalResponse = new APINormalResponse<>(apiResponseKeyMeta, data);
		return new ResponseEntity<>(apiNormalResponse, apiResponseKeyMeta.getStatus());
	}

	@Override
	public <T> ResponseEntity<APINormalResponse<T>> respond(T data, APIResponseType responseType, HttpStatus status) {
		APIResponseKeyMeta apiResponseKeyMeta = new APIResponseKeyMeta(responseType, status);
		APINormalResponse<T> apiNormalResponse = new APINormalResponse<>(apiResponseKeyMeta, data);
		return new ResponseEntity<>(apiNormalResponse, apiResponseKeyMeta.getStatus());
	}

	@Override
	public ResponseEntity<APIErrorResponse> respondWithError(APIErrorResponse response) {
		return new ResponseEntity<>(response, response.getMeta().getStatus());
	}

	@Override
	public ResponseEntity<APIErrorResponse> respondWithError(Throwable exception) {
		APIErrorResponse apiErrorResponse = new APIErrorResponse(exception);
		return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getMeta().getStatus());
	}

	@Override
	public ResponseEntity<APIErrorResponse> respondWithError(Throwable exception, APIResponseType responseType,
			HttpStatus status) {
		APIErrorResponse apiErrorResponse = new APIErrorResponse(exception, status);
		return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getMeta().getStatus());
	}

}
