package com.gateway.dlna;

import android.app.Activity;
import android.content.Context;
import android.media.MediaRouter;
import android.media.MediaRouter.RouteGroup;
import android.media.MediaRouter.RouteInfo;
import android.os.Bundle;

public class MediaRounterActivity extends Activity{

	private MediaRouter mMediaRouter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mMediaRouter = (MediaRouter) getSystemService(Context.MEDIA_ROUTER_SERVICE);
		mMediaRouter.addCallback(MediaRouter.ROUTE_TYPE_USER, new MediaRouter.Callback() {
			
			@Override
			public void onRouteVolumeChanged(MediaRouter router, RouteInfo info) {
				
			}
			
			@Override
			public void onRouteUnselected(MediaRouter router, int type, RouteInfo info) {
				
			}
			
			@Override
			public void onRouteUngrouped(MediaRouter router, RouteInfo info,
					RouteGroup group) {
				
			}
			
			@Override
			public void onRouteSelected(MediaRouter router, int type, RouteInfo info) {
				
			}
			
			@Override
			public void onRouteRemoved(MediaRouter router, RouteInfo info) {
				
			}
			
			@Override
			public void onRouteGrouped(MediaRouter router, RouteInfo info,
					RouteGroup group, int index) {
				
			}
			
			@Override
			public void onRouteChanged(MediaRouter router, RouteInfo info) {
				
			}
			
			@Override
			public void onRouteAdded(MediaRouter router, RouteInfo info) {
				
			}
		});
	}
}
