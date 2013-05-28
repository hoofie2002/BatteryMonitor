package com.empired.android.batterymonitor;

public interface BatteryStatus {

	public int getBatteryChargePercentage();

	public boolean isBatteryCharging();

	public boolean isBatteryFull();
	
	public boolean isBatteryOnAC();

	public boolean isBatteryOnUSB();

}