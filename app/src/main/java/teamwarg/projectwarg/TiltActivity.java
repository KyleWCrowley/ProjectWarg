package teamwarg.projectwarg;

/**
 * Created by Eli on 12/8/2014.
 */

import android.app.Activity;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;

import java.lang.Math;

public class TiltActivity extends Activity implements SensorEventListener {

    // Movement variables
    private TextView pitchText;
    private TextView yawText;
    private TextView rollText;
    private Button takeoffButton;
    private Button controlButton;
    // Sensor Variables
    private SensorManager sManager;
    private SensorManager gyroManager;
    // Control Variables
    private int rotationLevel;
    private boolean isControlling;
    private boolean isAirborne;
    private IARDrone drone;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilt);

        //get the TextViews from the layout file
        pitchText = (TextView) findViewById(R.id.pitch_text);
        yawText = (TextView) findViewById(R.id.yaw_text);
        rollText = (TextView) findViewById(R.id.roll_text);
        takeoffButton = (Button) findViewById(R.id.takeoff_button);
        takeoffButton.setEnabled(true);
        takeoffButton.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {

                    if( !isAirborne ) {
                        Log.d("Button Pressed: ", "Takeoff\n") ;
                        isAirborne = true;
                        takeoffButton.setText("Land");
                        drone.takeOff();
                        drone.hover();
                    }
                    else {
                        Log.d("Button Pressed: ", "Land\n");
                        isAirborne = false;
                        takeoffButton.setText("Takeoff");
                        drone.landing();
                    }

                }
            }
        );

        controlButton = (Button) findViewById(R.id.control_button);
        controlButton.setEnabled(false);
        controlButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        if( !isControlling ) {
                            Log.d("Button Pressed: ", "Control\n");
                            isControlling = true;
                            controlButton.setText("Stop Control");
                            controlButton.setEnabled(true);
                        }
                        else {
                            Log.d("Button Pressed: ", "Stop Control\n");
                            isControlling = false;
                            controlButton.setText("Control Drone");
                            controlButton.setEnabled(true);
                        }

                    }
                }
        );
        isControlling = false;
        isAirborne = false;

        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        rotationLevel = 0;

        YADroneApplication app = (YADroneApplication) getApplication();
        drone = app.getARDrone();
        drone.start();

/*        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                new VideoProxy(drone.getCommandManager()).start();
            }
        });
*/
    }

    //when this Activity starts
    @Override
    protected void onResume()
    {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
        gyroManager.registerListener(this, gyroManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_FASTEST);
    }

    //When this Activity isn't visible anymore
    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        gyroManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }
/*
        String x = String.format("%.3f",event.values[0]);
        String y = String.format("%.3f",event.values[1]);
        String z = String.format("%.3f",event.values[2]);
*/
        // Gyroscope Sensor Change
        if( event.sensor.getType() == Sensor.TYPE_GYROSCOPE ) {

            if( event.values[0] > 0.25 ) {
                rotationLevel++;
            }
            else if( event.values[0] < -0.25 ) {
                rotationLevel--;
            }

            int rotationEstimate = (int)(rotationLevel/50);
            if( rotationEstimate > 0 /* Left Yaw*/  ) {
                yawText.setText("Yaw level: " + Integer.toString(rotationEstimate));
                if( isControlling )
                    drone.getCommandManager().spinLeft(rotationEstimate*5);
            }
            else if( rotationEstimate < 0 /* Right Yaw */ ) {
                yawText.setText("Yaw level: " + Integer.toString(rotationEstimate));
                if( isControlling )
                    drone.getCommandManager().spinRight(Math.abs(rotationEstimate)*5);
            }
            else {
                yawText.setText("Yaw level: Default");
            }

        }

        if( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {

            int yEstimate = (int)event.values[1];
            int zEstimate = (int)event.values[2];

            if( zEstimate >= 1 /* Backward Pitch */ ) {
                pitchText.setText("Pitch level: " + Integer.toString(zEstimate*(-1)));
                if( isControlling )
                    drone.getCommandManager().forward(zEstimate * 5);
            }
            else if( zEstimate <= -1 /* Forward Pitch */ ) {
                pitchText.setText("Pitch level: " + Integer.toString(zEstimate*(-1)));
                if( isControlling )
                    drone.getCommandManager().backward(Math.abs(zEstimate) * 5);
            }
            else {
                pitchText.setText("Pitch level: Default");
            }

            if( yEstimate >= 1 /* Right Roll */ ) {
                rollText.setText("Roll level: " + Integer.toString(yEstimate));
                if( isControlling )
                    drone.getCommandManager().goRight(yEstimate*5);
            }
            else if( yEstimate <= -1 /* Left Roll */ ) {
                rollText.setText("Roll level: " + Integer.toString(yEstimate));
                if( isControlling )
                    drone.getCommandManager().goLeft(Math.abs(yEstimate)*5);
            }
            else {
                rollText.setText("Roll level: Default");
            }
        }


        if( !isControlling ) {

            drone.getCommandManager().hover();

            if (yawText.getText().toString() == "Yaw level: Default" &&
                    pitchText.getText().toString() == "Pitch level: Default" &&
                        rollText.getText().toString() == "Roll level: Default" &&
                            isAirborne ) {
                controlButton.setEnabled(true);

            } else {
                controlButton.setEnabled(false);

            }
        }
    }
}