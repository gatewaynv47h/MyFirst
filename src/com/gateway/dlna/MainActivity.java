package com.gateway.dlna;

import java.util.Iterator;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import com.gateway.dlna.service.DLNAService;
import com.gateway.dlna.service.DeviceDisplay;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private ArrayAdapter<DeviceDisplay> mListAdapter;
	private AndroidUpnpService mUpnpService;
	private ServiceConnection mUpnpServiceConnection;
	private BrowserRegistryListener mRegistryListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListAdapter = new ArrayAdapter<DeviceDisplay>(this,
				android.R.layout.simple_list_item_1);
		setListAdapter(mListAdapter);

		mUpnpServiceConnection = new UpnpServiceConnection();
		mRegistryListener = new BrowserRegistryListener();
		getApplicationContext().bindService(
				new Intent(this, DLNAService.class), mUpnpServiceConnection,
				Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mUpnpService != null)
			mUpnpService.getRegistry().removeListener(mRegistryListener);

		if (mUpnpServiceConnection != null)
			getApplicationContext().unbindService(mUpnpServiceConnection);

	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
	    menu.add(0, 0, 0,"搜索局域网") 
	        .setIcon(android.R.drawable.ic_menu_search); 
	    return true; 
	} 
	 
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) { 
	    if (item.getItemId() == 0 && mUpnpService != null) { 
	    	mUpnpService.getRegistry().removeAllRemoteDevices(); 
	    	mUpnpService.getControlPoint().search(); 
	    } 
	    return false; 
	} 
	
	private class UpnpServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mUpnpService = (AndroidUpnpService) service;
			
			mListAdapter.clear();
			
			for (Device device: mUpnpService.getRegistry().getDevices()) 
					mRegistryListener.deviceAdded(device);
			
			mUpnpService.getRegistry().addListener(mRegistryListener);
			//搜索所以异步设备
			mUpnpService.getControlPoint().search();
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mUpnpService = null;
		}
	}

	/**
	 * Upnp注册监听
	 * 
	 * @author windows7
	 * 
	 */
	private class BrowserRegistryListener implements RegistryListener {

		@Override
		public void afterShutdown() {

		}

		@Override
		public void beforeShutdown(Registry arg0) {

		}

		@Override
		public void localDeviceAdded(Registry arg0, LocalDevice arg1) {
			deviceAdded(arg1);
		}

		@Override
		public void localDeviceRemoved(Registry arg0, LocalDevice arg1) {
			deviceRemoved(arg1);
		}

		@Override
		public void remoteDeviceAdded(Registry arg0, RemoteDevice arg1) {
			deviceAdded(arg1);
		}

		@Override
		public void remoteDeviceDiscoveryFailed(Registry arg0,
				final RemoteDevice arg1,final Exception arg2) {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(
							MainActivity.this,
							"Discovery failed of '"
									+ arg1.getDisplayString()
									+ "': "
									+ (arg2 != null ? arg2.toString()
											: "Couldn't retrieve device/service descriptors"),
							Toast.LENGTH_LONG).show();

				}
			});

			
		}

		@Override
		public void remoteDeviceDiscoveryStarted(Registry arg0,
				RemoteDevice arg1) {

			deviceAdded(arg1);
		}

		@Override
		public void remoteDeviceRemoved(Registry arg0, RemoteDevice arg1) {
			deviceRemoved(arg1);
		}

		@Override
		public void remoteDeviceUpdated(Registry arg0, RemoteDevice arg1) {
			deviceAdded(arg1);

		}
		
		public void deviceAdded(final Device device){
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					DeviceDisplay dev = new DeviceDisplay(device);
					int position = mListAdapter.getPosition(dev);
					if(position > 0){
						mListAdapter.remove(dev);
						mListAdapter.insert(dev, position);
					}else{
						mListAdapter.add(dev);
					}
				}
			});
		}

		public void deviceRemoved(final Device device){
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mListAdapter.remove(new DeviceDisplay(device));
				}
			});
		}
		
	}

}
