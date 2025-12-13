package br.com.luishmalafaia.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}