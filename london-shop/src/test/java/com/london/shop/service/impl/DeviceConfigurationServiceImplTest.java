package com.london.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.london.shop.dtos.DeviceDTO;
import com.london.shop.enums.DeviceStatus;
import com.london.shop.service.DeviceService;

@ExtendWith(MockitoExtension.class)
class DeviceConfigurationServiceImplTest {
	
	@InjectMocks
	DeviceConfigurationServiceImpl deviceConfigService;
	
	@Mock
	DeviceService deviceService;
	
	@Test
	@DisplayName("Configure device service Return Success")
	void testConfigureDeviceReturnOK() {
		Long id = 1L;
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setStatus(DeviceStatus.ACTIVE);
		deviceDTO.setTemperature("4");
		when(deviceService.updateDevice(any(DeviceDTO.class), any(Long.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		ResponseEntity<Object> res = deviceConfigService.configureDevice(id);
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

}
