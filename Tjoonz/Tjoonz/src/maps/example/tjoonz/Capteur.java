package maps.example.tjoonz;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Capteur implements SensorEventListener {

	float x, y, z;

	public void onSensorChanged(SensorEvent event) {
		float[] values = event.values;

		x = values[0];

		y = values[1];

		z = values[2];
		
		System.out.println("woooooooooooooooooooooooop");
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}