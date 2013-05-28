package com.empired.android.batterymonitor.impl;

import com.empired.android.batterymonitor.BatteryStatus;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryStatusImpl implements BatteryStatus {

	
	private Intent batteryIntent;
	
	public BatteryStatusImpl(Context context) {
		this.batteryIntent  = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	
	public int getBatteryChargePercentage() {
    	int level = this.batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    	int scale = this.batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

    	return Math.round((level / (float)scale) * 100);
    }

	public boolean isBatteryCharging() {
        int status = this.batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING;
    }
	
	public boolean isBatteryFull() {
        int status = this.batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_FULL;
    }

	public boolean isBatteryOnAC() {
        int status = this.batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return status == BatteryManager.BATTERY_PLUGGED_AC;
    }

	public boolean isBatteryOnUSB() {
        int status = this.batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return status == BatteryManager.BATTERY_PLUGGED_USB;
    }

    
}
