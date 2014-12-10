//////////////////////////////////////////////
//              PROJECT WARG                //
//                                          //
//              KYLE CROWLEY                //
//              MITCHELL MARTINEZ           //
//              ELI GABAY                   //
//              ERIC GILCHRIST              //
//                                          //
//////////////////////////////////////////////
package teamwarg.projectwarg;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.yadrone.base.IARDrone;


//  Author: Eli Gabay

//  This class creates the main menu of the app
public class MenuActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initialize();
    }

    //  Function written by: Kyle Crowley
    //  Initializes the applications connection to the drone
    //  Drone must be previously connected to by the phone's wifi
    private void initialize() {
        YADroneApplication app = (YADroneApplication) getApplication();
        IARDrone drone = app.getARDrone();
        drone.start();
    }

    //  Called by Control Drone button
    //  Transitions app to the drone control activity
    public void buttonDroneControl(View view) {
        Intent intent = new Intent(this, ControlActivity.class);
        startActivity(intent);
    }

    //  Called by Settings button
    //  Transitions app to the settings activity
    public void buttonSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //  Called by Accelerometer button
    //  Transitions app to the accelerometer activity
    public void buttonAccelerometer(View view) {
        Intent intent = new Intent(this, AccelerometerActivity.class);
        startActivity(intent);
    }

    //  Called by Gyroscope button
    //  Transitions app to the gyroscope activity
    public void buttonGyroscope(View view) {
        Intent intent = new Intent(this, GyroscopeActivity.class);
        startActivity(intent);
    }

    //  Called by Help button
    //  Transitions app to the help activity
    public void buttonHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    //  Called by Exit Application button
    //  Ends activities and closes the app from inside the main activity
    public void buttonExit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        YADroneApplication app = (YADroneApplication) getApplication();
        IARDrone drone = app.getARDrone();
        drone.stop();

        startActivity(intent);
    }
}
