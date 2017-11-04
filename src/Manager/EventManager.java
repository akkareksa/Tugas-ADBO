/**
 * Kelas ini akan mengatur obstacle untuk muncul ke permainan 
 */
package Manager;
import Obstacles.*;

/**
 *
 * @author Kevin
 */
public class EventManager {

    private Obstacle obstacles;
    
    public EventManager(Obstacle obstacle) {
       this.obstacles=obstacle;
    }
    
    /**
     * 
     */
    public void doSomething(){
        obstacles.respawn(1f);
    }
    
    
}
