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

//Code sourced from the YADrone Project
//http://vsis-www.informatik.uni-hamburg.de/oldServer/teaching//projects/yadrone/
import android.app.Application;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;

public class YADroneApplication extends Application {
    /**
     * The drone is kept in the application context so that all activities use the same drone instance
     */
    private IARDrone drone;

    public void onCreate() {
        drone = new ARDrone("192.168.1.1", null); // null because of missing video support on Android
    }

    public IARDrone getARDrone() {
        return drone;
    }
}
