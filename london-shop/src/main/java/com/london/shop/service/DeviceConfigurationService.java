package com.london.shop.service;

import org.springframework.http.ResponseEntity;

public interface DeviceConfigurationService {
	
	public ResponseEntity<Object> configureDevice(Long id);

}
