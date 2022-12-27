package com.london.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.london.shop.dtos.DeviceDTO;
import com.london.shop.dtos.DeviceDTO.DeviceDTOCreation;
import com.london.shop.dtos.DeviceDTO.DeviceDTOUpdate;
import com.london.shop.dtos.ErrorBody;
import com.london.shop.entity.Device;
import com.london.shop.service.DeviceService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;

@RestController
@Validated
@Tag(name = "Device APIs")
public class DeviceController {

	@Autowired
	DeviceService deviceService;
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Device.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorBody.class), mediaType = "application/json")), })
	@PostMapping("/devices")
	public ResponseEntity<Object> addDevice(@Validated({DeviceDTOCreation.class }) @RequestBody DeviceDTO body) {

		return deviceService.createDevice(body);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Device.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorBody.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class), mediaType = "application/json")),})
	@PutMapping("/devices")
	public ResponseEntity<Object> updateDevice(@Validated({DeviceDTOUpdate.class }) @RequestBody DeviceDTO body, @RequestParam Long id) {

		return deviceService.updateDevice(body, id);
	}
	
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Device.class), mediaType = "application/json")),
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class), mediaType = "application/json")),})
	@DeleteMapping("/devices")
	public ResponseEntity<Object> deleteDevice(@RequestParam  Long id) {

		return deviceService.removeDevice(id);
	}
	
	@GetMapping("/devices")
	public ResponseEntity<Object> getDevices(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "1000") int pageSize) {

		return deviceService.getActiveDevices(pageNo, pageSize);
	}
	
	@Generated
	@PostMapping("/randomDevices")
	public String randomValues() {
		deviceService.addRandomDevices();
		return "ok";
	}

}
