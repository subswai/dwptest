package uk.gov.gsi.dwp.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public class ExceptionResponse {
	private Date timestamp;
	private Integer status;
	private String message;

	public ExceptionResponse(Date timestamp, Integer status, String message) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
	}
}
