package com.london.shop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.london.shop.dtos.DeviceDTO;
import com.london.shop.enums.DeviceStatus;

public class DeviceConfigurationConstraintValidator
		implements ConstraintValidator<DeviceConfigureConstraint, DeviceDTO> {
	
	@Override
	public void initialize(DeviceConfigureConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(DeviceDTO value, ConstraintValidatorContext context) {
		DeviceStatus status = value.getStatus();
		String temperature = value.getTemperature();
		int tempInt = Integer.parseInt(temperature);
		System.out.println(tempInt);
		String message = "";
		boolean valid = true;
		if ((status.equals(DeviceStatus.ACTIVE)) && !(0 <= tempInt && tempInt <= 10)) {
			message = "active devices must have temprature between 0 and 10";
			valid = false;
		} else if ((status.equals(DeviceStatus.READY)) && (tempInt != -1)) {
			message = "ready devices must have temprature -1";
			valid = false;

		}
		if (!valid) {
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation()
					.disableDefaultConstraintViolation();
		}
		return valid;
	}

}
