package com.london.shop.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.london.shop.enums.DeviceStatus;
import com.london.shop.validators.DeviceConfigureConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
@DeviceConfigureConstraint(groups =  com.london.shop.dtos.DeviceDTO.DeviceDTOCreation.class)
public class DeviceDTO {
	public interface DeviceDTOCreation {
	}

	public interface DeviceDTOUpdate {
	}

	@NotNull(message = "null values not allowed", groups = { DeviceDTOCreation.class })
	private DeviceStatus status;

	@NotNull(message = "null values not allowed", groups = { DeviceDTOCreation.class })
	@NotEmpty(message = "empty values not allowed", groups = { DeviceDTOCreation.class })
	@Pattern(message = "allowed values from -1 to 10", regexp = "^([0-9]|10|-1)$", groups = { DeviceDTOCreation.class,
			DeviceDTOUpdate.class })
	private String temperature;

	@NotNull(message = "null values not allowed", groups = { DeviceDTOCreation.class })
	@NotEmpty(message = "empty values not allowed", groups = { DeviceDTOCreation.class })
	@Pattern(message = "pinCode must be positive 7 digits", regexp = "^([0-9]{7})$", groups = { DeviceDTOCreation.class,
			DeviceDTOUpdate.class })
	private String pinCode;

}
