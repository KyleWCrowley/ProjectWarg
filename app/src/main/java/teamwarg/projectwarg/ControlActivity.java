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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import de.yadrone.base.IARDrone;

//  Author: Kyle Crowley

//  This class creates the drone control activity
public class ControlActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        createControls();
    }

    //  This function initializes listening for individual button inputs
    //  This function utilizes modified code from the YADrone project
    //  http://vsis-www.informatik.uni-hamburg.de/oldServer/teaching//projects/yadrone/
    private void createControls() {
        YADroneApplication app = (YADroneApplication) getApplication();
        final IARDrone drone = app.getARDrone();

        Button forward = (Button) findViewById(R.id.cmdForward);
        forward.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().forward(10);          //The degree of drone reaction, Default: 10
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

        Button backward = (Button) findViewById(R.id.cmdBackward);
        backward.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().backward(10);         //The degree of drone reaction, Default: 10
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });


        Button left = (Button) findViewById(R.id.cmdLeft);
        left.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().goLeft(10);           //The degree of drone reaction, Default: 10
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });


        Button right = (Button) findViewById(R.id.cmdRight);
        right.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().goRight(10);          //The degree of drone reaction, Default: 10
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

        Button up = (Button) findViewById(R.id.cmdUp);
        up.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().up(40);               //The degree of drone reaction, Default: 40
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

        Button down = (Button) findViewById(R.id.cmdDown);
        down.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().down(40);             //The degree of drone reaction, Default: 40
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });


        Button spinLeft = (Button) findViewById(R.id.cmdLeftYaw);
        spinLeft.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().spinLeft(20);         //The degree of drone reaction, Default: 20
                else if (event.getAction() == MotionEvent.ACTION_UP)

                    drone.hover();

                return true;
            }
        });

        //  Controls the right yaw of the drone.
        Button spinRight = (Button) findViewById(R.id.cmdRightYaw);
        spinRight.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().spinRight(20);        //The degree of drone reaction, Default: 20
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

        //  Creates a toggle button that switches between initiating
        //  takeoff and landing of the drone.
        final Button landing = (Button) findViewById(R.id.cmdLandToggle);
        landing.setOnClickListener(new View.OnClickListener() {
            boolean isFlying = false;

            public void onClick(View v) {
                if (!isFlying) {
                    drone.takeOff();
                    landing.setText("Landing");
                } else {
                    drone.landing();
                    landing.setText("Take Off");
                }
                isFlying = !isFlying;
            }
        });

        //  Triggers emergency shutdown of drone
        //  After this is used, drone must either have emergency mode reset
        //  or be power cycled before flight will be allowed again.
        Button emergency = (Button) findViewById(R.id.cmdEmergency);
        emergency.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drone.reset();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}