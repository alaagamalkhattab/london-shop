package com.london.shop.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.london.shop.service.DeviceConfigurationService;

@WebMvcTest(controllers = DeviceConfigurationController.class)
class DeviceConfigurationControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	DeviceConfigurationService deviceConfigService;

	@Test
	@DisplayName("config device, successful senario")
	void testConfigDeviceREturnOK() throws Exception {
	
		RequestBuilder requestBuiler = MockMvcRequestBuilders.post("/deviceConfigurations")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).param("id", "1234");;
		when(deviceConfigService.configureDevice(1234L))
		.thenReturn(new ResponseEntity<>( HttpStatus.OK));
		
		MvcResult mvcResult = mockMvc.perform(requestBuiler).andReturn();
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

}
