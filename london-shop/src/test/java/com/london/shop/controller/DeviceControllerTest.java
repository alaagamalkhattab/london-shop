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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.london.shop.dtos.DeviceDTO;
import com.london.shop.enums.DeviceStatus;
import com.london.shop.service.DeviceService;


@WebMvcTest(controllers = DeviceController.class)
class DeviceControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	DeviceService deviceService;

	@Test
	@DisplayName("add device, successful senario")
	void testAddDeviceReturnOK() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO(DeviceStatus.ACTIVE, "1", "1234567");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(deviceDTO);

		RequestBuilder requestBuiler = MockMvcRequestBuilders.post("/devices").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		when(deviceService.createDevice(deviceDTO))
		.thenReturn(new ResponseEntity<>( HttpStatus.CREATED));
		
		MvcResult mvcResult = mockMvc.perform(requestBuiler).andReturn();
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	@Test
	@DisplayName("update device, successful senario")
	void testUpdateDeviceReturnOK() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO(DeviceStatus.ACTIVE, "1", "1234567");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(deviceDTO);

		RequestBuilder requestBuiler = MockMvcRequestBuilders.put("/devices").content(jsonBody).param("id", "123")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		when(deviceService.updateDevice(deviceDTO, 123L))
		.thenReturn(new ResponseEntity<>( HttpStatus.OK));
		
		MvcResult mvcResult = mockMvc.perform(requestBuiler).andReturn();
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	@DisplayName("delete device, successful senario")
	void testDeleteDeviceReturnOK() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO(DeviceStatus.ACTIVE, "1", "1234567");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(deviceDTO);

		RequestBuilder requestBuiler = MockMvcRequestBuilders.delete("/devices").param("id", "123")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		when(deviceService.removeDevice(123L))
		.thenReturn(new ResponseEntity<>( HttpStatus.OK));
		
		MvcResult mvcResult = mockMvc.perform(requestBuiler).andReturn();
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("get devices, successful senario")
	void testGetDevicesReturnOK() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO(DeviceStatus.ACTIVE, "1", "1234567");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(deviceDTO);

		RequestBuilder requestBuiler = MockMvcRequestBuilders.get("/devices").param("pageNo", "0").param("pageSize", "1000")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		when(deviceService.getActiveDevices(0, 1000))
		.thenReturn(new ResponseEntity<>( HttpStatus.OK));
		
		MvcResult mvcResult = mockMvc.perform(requestBuiler).andReturn();
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
}
