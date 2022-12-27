package com.london.shop.service;

import org.springframework.http.ResponseEntity;

import com.london.shop.dtos.DeviceDTO;

public interface DeviceService {

	public ResponseEntity<Object> createDevice(DeviceDTO device);
	public ResponseEntity<Object> updateDevice(DeviceDTO device, Long id);
	public ResponseEntity<Object> removeDevice(Long id);
	public ResponseEntity<Object> getActiveDevices(int pageNo, int pageSize);
	public void addRandomDevices();
	
}
