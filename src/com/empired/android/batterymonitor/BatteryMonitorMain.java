package com.empired.android.batterymonitor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

import com.empired.android.batterymonitor.impl.BatteryStatusImpl;

public class BatteryMonitorMain extends Activity {
	
	private static Context context;
	
	SensorManager mSensorManager;
	BatteryManager mBatteryManager;
	SensorTrap st;
	TextView tvSensorX;
	TextView tvSensorY;
	TextView batChargeStatus;
	TextView batChargeState;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        BatteryMonitorMain.context = getApplicationContext();
        
        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        
        TextView myText = (TextView) findViewById(R.id.textView1);
        tvSensorX  = (TextView) findViewById(R.id.sensorX);
        tvSensorY  = (TextView) findViewById(R.id.sensorY);
        batChargeStatus = (TextView) findViewById(R.id.batteryCharge);
        batChargeState = (TextView) findViewById(R.id.batteryChargeState);
        myText.setText("Accelerometer Movements");
        
        st = new SensorTrap();
        st.startReading();
        
    }
    


    
    public static Context getAppContext() {
        return BatteryMonitorMain.context;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
             // Start the simulation
        st.startReading();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the simulation
        st.stopReading();
    }
    
    
    class SensorTrap implements SensorEventListener {
    	Sensor mAccelerometer;
    	BatteryStatus batStatus = null;
    	
    	public SensorTrap() {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            batStatus = new BatteryStatusImpl(getAppContext());
    	}
    	
        public void startReading() {
            /*
             * It is not necessary to get accelerometer events at a very high
             * rate, by using a slower rate (SENSOR_DELAY_UI), we get an
             * automatic low-pass filter, which "extracts" the gravity component
             * of the acceleration. As an added benefit, we use less power and
             * CPU resources.
             */
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }

        public void stopReading() {
            mSensorManager.unregisterListener(this);
        }
    	
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                return;
            /*
             * record the accelerometer data, the event's timestamp as well as
             * the current time. The latter is needed so we can calculate the
             * "present" time during rendering. In this application, we need to
             * take into account how the screen is rotated with respect to the
             * sensors (which always return data in a coordinate space aligned
             * to with the screen in its native orientation).
             */

            float mSensorX = event.values[0];
            tvSensorX.setText("xPos: " + String.valueOf(mSensorX));
            float mSensorY = event.values[1];
            tvSensorY.setText("yPos: " + String.valueOf(mSensorY));
            float batCharge = batStatus.getBatteryChargePercentage();
            batChargeState.setText("Charge :" + String.valueOf(batCharge) + "%");
            boolean isBatCharging= batStatus.isBatteryCharging();
            batChargeStatus.setText("Charged  :" + String.valueOf(isBatCharging));
            
        }

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
    }
    
}

