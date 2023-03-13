package com.nuracell.bs.service.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

public class StorageException extends RuntimeException {

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
