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
        this.charging = status == BatteryManager.BATTERY_STATUS_CHARGING;
        this.full = status == BatteryManager.BATTERY_STATUS_FULL;

        status = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        this.ac = status == BatteryManager.BATTERY_PLUGGED_AC;
        this.usb = status == BatteryManager.BATTERY_PLUGGED_USB;
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
	public boolean isBatteryFull() {
		return this.full;
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
