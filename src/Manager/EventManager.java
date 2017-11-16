/**
 * Kelas ini akan mengatur obstacle untuk muncul ke permainan
 */
package Manager;

import Enemies.*;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import java.util.Random;

/**
 *
 * @author Kevin Naofal Gerry Firman Andrianto
 */
public class EventManager  {

    private Obstacle[] obstacles;


    public EventManager(Obstacle[] obstacles) {
        this.obstacles = obstacles;

    }
   

    public void newSpawnStyle() {
        
        
        obstacles[0].respawnFirst(1f);
        obstacles[1].respawnSecond(1f, obstacles[0]);
    }
}

  
