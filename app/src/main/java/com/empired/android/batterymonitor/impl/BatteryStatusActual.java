package com.empired.android.batterymonitor.impl;

import android.content.Intent;
import android.os.BatteryManager;

import com.empired.android.batterymonitor.BatteryStatus;

public class BatteryStatusActual implements BatteryStatus {

	private int percentage = 0;
	private boolean charging;
	private boolean full;
	private boolean ac;
	private boolean usb;
	
	public BatteryStatusActual(Intent batteryIntent) {
    	int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
    	int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);

    	if (level > 0) {
    		this.percentage =  Math.round((level / (float)scale) * 100);
    	}

        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
    	if (status == BatteryManager.BATTERY_STATUS_CHARGING ) {
    		this.charging = true;
		} else {
    		this.charging = false;
		}
		if (status == BatteryManager.BATTERY_STATUS_FULL) {
			this.full = true;
		} else {
			this.full= false;
		}

        status = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
		if (status == BatteryManager.BATTERY_PLUGGED_AC) {
			this.ac = true;
		} else {
			this.ac= false;
		}
		if (status == BatteryManager.BATTERY_PLUGGED_USB) {
			this.usb = true;
		} else {
			this.usb= false;
		}
	}
	

	
	@Override
	public int getBatteryChargePercentage() {
		return this.percentage;
	}

	@Override
	public boolean isBatteryCharging() {
		return this.charging;
	}

	@Override
	public boolean isBatteryOnAC() {
		return this.ac;
	}

	@Override
	public boolean isBatteryOnUSB() {
		return this.usb;
	}

}
