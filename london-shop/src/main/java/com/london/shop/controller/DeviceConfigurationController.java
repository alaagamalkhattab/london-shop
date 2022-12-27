package com.london.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.london.shop.dtos.ErrorBody;
import com.london.shop.entity.Device;
import com.london.shop.service.DeviceConfigurationService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@Tag(name = "Device Configuration Service API")
public class DeviceConfigurationController {
	
	@Autowired
	DeviceConfigurationService deviceConfigService;

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Device.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorBody.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class), mediaType = "application/json")),})
	@PostMapping("/deviceConfigurations")
	public ResponseEntity<Object> configureDevice(@RequestParam Long id) {

		return deviceConfigService.configureDevice(id);
	}

}
