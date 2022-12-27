package com.london.shop.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.london.shop.dtos.DeviceDTO;
import com.london.shop.enums.DeviceStatus;
import com.london.shop.service.DeviceConfigurationService;
import com.london.shop.service.DeviceService;

@Service
public class DeviceConfigurationServiceImpl implements DeviceConfigurationService {

	@Autowired
	DeviceService deviceService;

	@Override
	public ResponseEntity<Object> configureDevice(Long id) {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setStatus(DeviceStatus.ACTIVE);
		deviceDTO.setTemperature(String.valueOf(generateRandomNumbers()));

		return deviceService.updateDevice(deviceDTO, id);
	}

	public static int generateRandomNumbers() {
		Random random = new Random();
		return random.nextInt(10) + 1;
	}

}
