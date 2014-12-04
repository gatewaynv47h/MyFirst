package com.gateway.dlna.service;

import org.fourthline.cling.model.meta.Device;


public class DeviceDisplay {
	Device device;

	public DeviceDisplay(Device device) {
		this.device = device;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;
		
		DeviceDisplay that = (DeviceDisplay) obj;
		return device.equals(that.device);
	}
	
	@Override
	public int hashCode() {
		return device.hashCode();
	}
	
	@Override
	public String toString() {
		
		return device.isFullyHydrated()?device.getDisplayString():device.getDisplayString()+" **";

	}
}
