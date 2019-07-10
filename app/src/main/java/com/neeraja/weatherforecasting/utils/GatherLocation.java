package com.neeraja.weatherforecasting.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class GatherLocation implements LocationListener {

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GatherLocation() {
		super();
	}

	public Location getLocation(Context context) {
		Location location = null;
		try {

			if (locationManager == null) {
				locationManager = (LocationManager) context
						.getSystemService(Context.LOCATION_SERVICE);
			}

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				// First get location from Network Provider
				if (isNetworkEnabled) {
					if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
						return null;
					}
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 0, 0, this);
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER, 0, 0, this);
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						}
					}
				}
			}

		} catch (Exception e) {
			Utils.logE(e.toString());
			location = null;
		}

		return location;
	}

	/**
	 * 
	 * Stop using GPS listener Calling this function will stop using GPS
	 * 
	 * */
	public void stopUsing() {
		try {
			if (locationManager != null) {
				locationManager.removeUpdates(GatherLocation.this);
				locationManager = null;
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
