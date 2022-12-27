package com.london.shop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.london.shop.entity.Device;
import com.london.shop.enums.DeviceStatus;

@Repository
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {
	List<Device> findByStatusOrderByPinCodeAsc(DeviceStatus status, Pageable pageable);
}
