package com.gateway.dlna.service;

import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;

import android.content.Intent;
import android.os.IBinder;

public class DLNAService extends AndroidUpnpServiceImpl {

	@Override
	public IBinder onBind(Intent intent) {
		return super.onBind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected UpnpServiceConfiguration createConfiguration() {

		
		return new AndroidUpnpServiceConfiguration(){
			@Override
			public int getRegistryMaintenanceIntervalMillis() {
				return 7000;
			}
		};
	}
	
}
