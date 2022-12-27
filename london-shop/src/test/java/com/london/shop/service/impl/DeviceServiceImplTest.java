package com.london.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.london.shop.dtos.DeviceDTO;
import com.london.shop.entity.Device;
import com.london.shop.enums.DeviceStatus;
import com.london.shop.exception.handler.DeviceException;
import com.london.shop.repository.DeviceRepository;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

	@InjectMocks
	DeviceServiceImpl deviceServiceImpl;

	@Mock
	DeviceRepository deviceRepository;

	@Mock
	ModelMapper modelMapper;

	@Test
	@DisplayName("Create device service Return Success")
	void testCreateDeviceReturnCreated() {
		Device device = new Device();
		DeviceDTO deviceDTO = new DeviceDTO();
		when(modelMapper.map(deviceDTO, Device.class)).thenReturn(device);
		when(deviceRepository.save(device)).thenReturn(device);

		ResponseEntity<Object> res = deviceServiceImpl.createDevice(deviceDTO);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}

	@Test
	@DisplayName("Create device service Return failure")
	void testCreateDeviceReturnException() throws DeviceException {

		assertThrows(DeviceException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				Device device = new Device();
				DeviceDTO deviceDTO = new DeviceDTO();
				when(modelMapper.map(deviceDTO, Device.class)).thenReturn(device);

				when(deviceRepository.save(device)).thenThrow(DataIntegrityViolationException.class);

				deviceServiceImpl.createDevice(deviceDTO);

			}
		});
	}

	@Test
	@DisplayName("Update device service Return Success")
	void testUpdateDeviceReturnOK() {
		Device device = new Device();
		device.setStatus(DeviceStatus.ACTIVE);
		device.setTemperature(10);
		DeviceDTO deviceDTO = new DeviceDTO();
		Long id = 1L;
		Optional<Device> deviceOptional = Optional.of(device);
		when(deviceRepository.findById(id)).thenReturn(deviceOptional);
		doNothing().when(modelMapper).map(deviceDTO, device);
		when(deviceRepository.save(device)).thenReturn(device);

		ResponseEntity<Object> res = deviceServiceImpl.updateDevice(deviceDTO, id);
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	@DisplayName("Update device service Return exception")
	void testUpdateDeviceReturnDeviceException() {
		assertThrows(DeviceException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				Device device = new Device();
				device.setStatus(DeviceStatus.ACTIVE);
				device.setTemperature(-1);
				DeviceDTO deviceDTO = new DeviceDTO();
				Long id = 1L;
				Optional<Device> deviceOptional = Optional.of(device);
				when(deviceRepository.findById(id)).thenReturn(deviceOptional);
				doNothing().when(modelMapper).map(deviceDTO, device);

				deviceServiceImpl.updateDevice(deviceDTO, id);

			}
		});
	}

	@Test
	@DisplayName("Update device service Return exception")
	void testUpdateDeviceReturnDataIntegrityException() {
		assertThrows(DeviceException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				Device device = new Device();
				device.setStatus(DeviceStatus.ACTIVE);
				device.setTemperature(10);
				DeviceDTO deviceDTO = new DeviceDTO();
				Long id = 1L;
				Optional<Device> deviceOptional = Optional.of(device);
				when(deviceRepository.findById(id)).thenReturn(deviceOptional);
				doNothing().when(modelMapper).map(deviceDTO, device);
				when(deviceRepository.save(device)).thenThrow(DataIntegrityViolationException.class);

				deviceServiceImpl.updateDevice(deviceDTO, id);

			}
		});
	}

	@Test
	@DisplayName("Update device service Return not found")
	void testUpdateDeviceReturnNotFound() {
		Device device = new Device();
		device.setStatus(DeviceStatus.ACTIVE);
		device.setTemperature(10);
		DeviceDTO deviceDTO = new DeviceDTO();
		Long id = 1L;
		Optional<Device> deviceOptional = Optional.empty();
		when(deviceRepository.findById(id)).thenReturn(deviceOptional);

		ResponseEntity<Object> res = deviceServiceImpl.updateDevice(deviceDTO, id);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	@DisplayName("Remove device service Return Success")
	void testRemoveDeviceReturnOK() {
		Device device = new Device();

		Long id = 1L;
		Optional<Device> deviceOptional = Optional.of(device);
		when(deviceRepository.findById(id)).thenReturn(deviceOptional);
		doNothing().when(deviceRepository).deleteById(id);

		ResponseEntity<Object> res = deviceServiceImpl.removeDevice(id);
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	@DisplayName("Remove device service Return not found")
	void testRemoveDeviceReturnNotFound() {

		Long id = 1L;
		Optional<Device> deviceOptional = Optional.empty();
		when(deviceRepository.findById(id)).thenReturn(deviceOptional);

		ResponseEntity<Object> res = deviceServiceImpl.removeDevice(id);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	@DisplayName("get active device service Return ok")
	void testGetActiveDevicesReturnOK() {
		int pageNo = 0;
		int pageSize = 1000;
		Pageable paging = PageRequest.of(pageNo, pageSize);
		when(deviceRepository.findByStatusOrderByPinCodeAsc(DeviceStatus.ACTIVE, paging))
				.thenReturn(new ArrayList<Device>());

		ResponseEntity<Object> res = deviceServiceImpl.getActiveDevices(pageNo, pageSize);
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	

}
