package teamwarg.projectwarg;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import de.yadrone.base.IARDrone;

public class ControlActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        createControls();
    }

    private void createControls() {
        YADroneApplication app = (YADroneApplication) getApplication();
        final IARDrone drone = app.getARDrone();

        Button forward = (Button) findViewById(R.id.cmdForward);
        forward.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().forward(10);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

        Button backward = (Button) findViewById(R.id.cmdBackward);
        backward.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().backward(10);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });


        Button left = (Button) findViewById(R.id.cmdLeft);
        left.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().goLeft(10);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });


        Button right = (Button) findViewById(R.id.cmdRight);
        right.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().goRight(10);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

        Button up = (Button) findViewById(R.id.cmdUp);
        up.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().up(40);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

        Button down = (Button) findViewById(R.id.cmdDown);
        down.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().down(40);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });


        Button spinLeft = (Button) findViewById(R.id.cmdLeftYaw);
        spinLeft.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().spinLeft(20);
                else if (event.getAction() == MotionEvent.ACTION_UP)

                    drone.hover();

                return true;
            }
        });


        Button spinRight = (Button) findViewById(R.id.cmdRightYaw);
        spinRight.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drone.getCommandManager().spinRight(20);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drone.hover();

                return true;
            }
        });

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