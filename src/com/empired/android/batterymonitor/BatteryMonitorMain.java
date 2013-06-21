package com.empired.android.batterymonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empired.android.batterymonitor.impl.BatteryStatusActual;

public class BatteryMonitorMain extends Activity {

	private static Context context;

	TextView chargePercent;
	ImageView batteryImage;
	TextView chargeStatus;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		BatteryMonitorMain.context = getApplicationContext();

		chargePercent = (TextView) findViewById(R.id.chargePercent);
		batteryImage = (ImageView)findViewById(R.id.batteryImage);
		chargeStatus = (TextView)findViewById(R.id.txtChargingStatus);

	}

	/**
	 * Once onResume is called, the activity has become visible (it is now
	 * "resumed"). Comes after onCreate
	 */
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();

		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mBroadcastReceiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);
	}
	
	private int[] batteryIcons = new int[] {
			R.drawable.battery_001,
			R.drawable.battery_002,
			R.drawable.battery_003,
			R.drawable.battery_004,
			R.drawable.battery_005,
			R.drawable.battery_006,
			R.drawable.battery_007,
			R.drawable.empty,
			R.drawable.full
	};

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();

			// store battery information received from BatteryManager
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				BatteryStatus stat = new BatteryStatusActual(intent);
				chargePercent.setText(String.valueOf(stat
						.getBatteryChargePercentage()) + "%");
				
				String msgChargingStatus = "On Battery";
				if (stat.isBatteryCharging()) {
					msgChargingStatus = "Charging via ";
					if (stat.isBatteryOnAC()) {
						msgChargingStatus = msgChargingStatus + "AC";
					}
					if (stat.isBatteryOnUSB()) {
						msgChargingStatus = msgChargingStatus + "USB";
					}
				}
				chargeStatus.setText(msgChargingStatus);
				
				if (stat.getBatteryChargePercentage() > 75) {
					chargePercent.setTextColor(Color.GREEN);
				}
				if (stat.getBatteryChargePercentage() <= 75 && stat.getBatteryChargePercentage() >=15 ) {
					chargePercent.setTextColor(Color.YELLOW);
				}
				if (stat.getBatteryChargePercentage() < 15) {
					chargePercent.setTextColor(Color.RED);
				}

				// Full
				if (stat.getBatteryChargePercentage() > 90) batteryImage.setImageResource(batteryIcons[8]);


				if (stat.getBatteryChargePercentage() < 16 && stat.getBatteryChargePercentage() >=8) batteryImage.setImageResource(batteryIcons[0]);
				if (stat.getBatteryChargePercentage() < 30 && stat.getBatteryChargePercentage() >=15 ) batteryImage.setImageResource(batteryIcons[1]);
				if (stat.getBatteryChargePercentage() < 40 && stat.getBatteryChargePercentage() >=30) batteryImage.setImageResource(batteryIcons[2]);
				if (stat.getBatteryChargePercentage() < 55 && stat.getBatteryChargePercentage() >=40 ) batteryImage.setImageResource(batteryIcons[3]);
				if (stat.getBatteryChargePercentage() < 70 && stat.getBatteryChargePercentage() >=55 ) batteryImage.setImageResource(batteryIcons[4]);
				if (stat.getBatteryChargePercentage() < 80 && stat.getBatteryChargePercentage() >=70 ) batteryImage.setImageResource(batteryIcons[5]);
				if (stat.getBatteryChargePercentage() < 90 && stat.getBatteryChargePercentage() >=80 ) batteryImage.setImageResource(batteryIcons[6]);

				
				
				// Empty
				if (stat.getBatteryChargePercentage() < 8) batteryImage.setImageResource(batteryIcons[7]);;

				
			}

		}
	};

}
