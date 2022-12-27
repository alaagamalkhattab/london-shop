package com.london.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.london.shop.dtos.DeviceDTO;
import com.london.shop.dtos.ErrorBody;
import com.london.shop.entity.Device;
import com.london.shop.enums.DeviceStatus;
import com.london.shop.exception.handler.DeviceException;
import com.london.shop.repository.DeviceRepository;
import com.london.shop.service.DeviceService;

import lombok.Generated;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	DeviceRepository deviceRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public ResponseEntity<Object> createDevice(DeviceDTO deviceDTO) {
		Device device = modelMapper.map(deviceDTO, Device.class);
		try {
			device = deviceRepository.save(device);
		} catch (DataIntegrityViolationException e) {
			throw new DeviceException(e.getMostSpecificCause().getMessage());
		}
		return new ResponseEntity<>(device, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Object> updateDevice(DeviceDTO deviceDTO, Long id) {

		Optional<Device> deviceOptional = deviceRepository.findById(id);
		if (deviceOptional.isPresent()) {
			Device device = deviceOptional.get();
			modelMapper.map(deviceDTO, device);
			boolean deviceConfig = checkDeviceStatusAndTemp(device);
			if (!deviceConfig) {
				throw new DeviceException("wrong device status and temperature values");
			}
			try {
				deviceRepository.save(device);
			} catch (DataIntegrityViolationException e) {
				throw new DeviceException(e.getMostSpecificCause().getMessage());
			}
			return new ResponseEntity<>(device, HttpStatus.OK);
		}
		ErrorBody error = new ErrorBody("no device found with id: " + id);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Object> removeDevice(Long id) {
		Optional<Device> deviceOptional = deviceRepository.findById(id);
		if (deviceOptional.isPresent()) {
			deviceRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		ErrorBody error = new ErrorBody("no device found with id: " + id);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Object> getActiveDevices(int pageNo, int pageSize) {
		Pageable paging =  PageRequest.of(pageNo, pageSize);
		List<Device> devices = deviceRepository.findByStatusOrderByPinCodeAsc(DeviceStatus.ACTIVE, paging);
		return new ResponseEntity<>(devices, HttpStatus.OK);
	}

	private boolean checkDeviceStatusAndTemp(Device device) {
		if (device.getStatus().equals(DeviceStatus.ACTIVE) && 0 <= device.getTemperature()
				&& device.getTemperature() <= 10) {
			return true;
		} else if (device.getStatus().equals(DeviceStatus.READY) && device.getTemperature() == -1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Generated
	public void addRandomDevices() {
		List<Device> list = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			Device device = Device.builder().status(DeviceStatus.ACTIVE)
					.temperature(DeviceConfigurationServiceImpl.generateRandomNumbers()).pinCode(String.format("%7s", String.valueOf(i)).replace(' ', '0')).build();


			list.add(device);
		}
		for (int i = 1000000; i < 2000000; i++) {
			Device device = Device.builder().status(DeviceStatus.READY)
					.temperature(-1).pinCode(String.format("%7s", String.valueOf(i)).replace(' ', '0')).build();


			list.add(device);
		}
		 deviceRepository.saveAll(list);

	}

}
