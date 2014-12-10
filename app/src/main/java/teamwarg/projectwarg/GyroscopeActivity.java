package teamwarg.projectwarg;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class GyroscopeActivity extends Activity implements SensorEventListener {

    //a TextView
    private TextView tv;
    //the Sensor Manager
    private SensorManager sManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        //get the TextView from the layout file
        tv = (TextView) findViewById(R.id.gyroscope_text);

        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    //when this Activity starts
    @Override
    protected void onResume() {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_FASTEST);
    }

    //When this Activity isn't visible anymore
    @Override
    protected void onStop() {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }

        String x = String.format("%.3f", event.values[0]);
        String y = String.format("%.3f", event.values[1]);
        String z = String.format("%.3f", event.values[2]);
        //else it will output the Roll, Pitch and Yawn values
        tv.setText("Gyroscope Text" + "\n" +
                "Rotation Rate About X:" + x + "\n" +
                "Rotation Rate About Y:" + y + "\n" +
                "Rotation Rate About Z:" + z);

    }
}