/**
 * Kelas ini akan mengatur obstacle untuk muncul ke permainan
 */
package Manager;

import Obstacles.*;
import com.jme3.math.Vector3f;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public class EventManager {
    
    private Obstacle[] obstacles;
    
    public EventManager(Obstacle[] obstacle) {
        this.obstacles = obstacle;
    }
    
    
    public void newSpawnStyle() {
        obstacles[0].respawnFirst(1f);
        obstacles[1].respawnSecond(1f,obstacles[0]);
    }
}