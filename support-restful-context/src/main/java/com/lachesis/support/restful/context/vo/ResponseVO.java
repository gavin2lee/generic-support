package com.lachesis.support.restful.context.vo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@SuppressWarnings("unchecked")
public class ResponseVO extends ResponseEntity<Object> {
	public static final ResponseVO NO_CONTENT = new ResponseVO(HttpStatus.NO_CONTENT);
	public static final ResponseVO NOT_FOUND = new ResponseVO(HttpStatus.NOT_FOUND);
	public static final ResponseVO INTERNAL_SERVER_ERROR = new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR);
	public static final ResponseVO UNAUTHORIZED = new ResponseVO(HttpStatus.UNAUTHORIZED);
	public static final ResponseVO FORBIDDEN = new ResponseVO(HttpStatus.FORBIDDEN);
	public static final ResponseVO CONFLICT = new ResponseVO(HttpStatus.CONFLICT);

	public static ResponseVO unAuthorized(Object body) {
		return new ResponseVO(body, HttpStatus.UNAUTHORIZED);
	}

	public static ResponseVO forbidden(Object body) {
		return new ResponseVO(body, HttpStatus.FORBIDDEN);
	}

	public static ResponseVO internalServerError(Object body) {
		return new ResponseVO(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static ResponseVO notFound(Object body) {
		return new ResponseVO(body,HttpStatus.NOT_FOUND);
	}

	public static ResponseVO ok(Object body) {
		return new ResponseVO(body);
	}

	public ResponseVO(Object body) {
		super(body, HttpStatus.OK);
	}

	public ResponseVO(MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(headers, statusCode);
	}

	public ResponseVO(Object body, HttpStatus statusCode) {
		super(body, statusCode);
	}

	public ResponseVO(Object body, MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(body, headers, statusCode);
	}

	public ResponseVO(HttpStatus statusCode) {
		super(statusCode);
	}

	
	public static class ResponseBuilder {
		private HttpStatus httpStatus = HttpStatus.OK;
		private Object body;
		private final HttpHeaders headers = new HttpHeaders();

		public ResponseBuilder(Object body) {
			this.body = body;
		}

		public ResponseVO build() {
			return new ResponseVO(body, headers, httpStatus);
		}

		public static ResponseBuilder body(Object body) {
			return new ResponseBuilder(body);
		}

		public ResponseBuilder status(HttpStatus status) {
			if (status != null) {
				this.httpStatus = status;
			}else{
				throw new IllegalArgumentException("status must be specified.");
			}
			return this;
		}

		public ResponseBuilder headers(HttpHeaders headers) {
			if (headers != null) {
				this.headers.putAll(headers);
			}
			return this;
		}
	}

}
