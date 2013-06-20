package com.empired.android.batterymonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.empired.android.batterymonitor.impl.BatteryStatusActual;

public class BatteryMonitorMain extends Activity {

	private static Context context;

	TextView batChargeStatus;
	TextView batChargeState;
	TextView chargePercent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		BatteryMonitorMain.context = getApplicationContext();

		batChargeStatus = (TextView) findViewById(R.id.batteryCharge);
		chargePercent = (TextView) findViewById(R.id.chargePercent);




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

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();

			// store battery information received from BatteryManager
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				BatteryStatus stat = new BatteryStatusActual(intent);
				batChargeStatus.setText("USB  :"
						+ String.valueOf(stat.isBatteryOnUSB()));
				chargePercent.setText(String.valueOf(stat
						.getBatteryChargePercentage()) + "%");
				if (stat.getBatteryChargePercentage() > 75) chargePercent.setTextColor(Color.GREEN);
				if (stat.getBatteryChargePercentage() < 15) chargePercent.setTextColor(Color.RED);
				if (stat.getBatteryChargePercentage() <= 75 && stat.getBatteryChargePercentage() >=15 ) chargePercent.setTextColor(Color.YELLOW);
				if (stat.getBatteryChargePercentage() < 15) chargePercent.setTextColor(Color.RED);
			}

		}
	};

}
