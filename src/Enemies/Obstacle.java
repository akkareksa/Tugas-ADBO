package Enemies;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author Kevin
 *         Naofal
 *         Gerry
 *         Firman
 *         Andrianto
 * 
 */
public abstract class Obstacle {

    private Spatial objectSpatial;
    private Random randomGenerator;
    private int temporaryRespawnX;

    public Obstacle() {
        this.objectSpatial = objectSpatial;
        randomGenerator = new Random();
    }

    public Vector3f getLocation() {
        return objectSpatial.getLocalTranslation();
    }

    public void moveForward(float score) {
        Vector3f v = this.objectSpatial.getLocalTranslation();
        this.objectSpatial.setLocalTranslation(v.x - score * 0.01f, v.y, v.z);
    }

    public Spatial getSpatial() {
        return this.objectSpatial;
    }
    
    public void setSpatial(AssetManager assetManager) {
        
    }

    public void setLocation(Vector3f location) {
        this.objectSpatial.setLocalTranslation(location);
    }

    public void respawnFirst(float score) {
        if (this.getLocation().x > -5) {
            this.moveForward(score);
        } else {
            int randomNum = 45+randomGenerator.nextInt(10);
            Vector3f newPosition = new Vector3f(randomNum, 0, 0);
            this.setLocation(newPosition);
        }
    }

    public void respawnSecond(float score,Obstacle otherObstacle) {
        if (this.getLocation().x > -5) {
            this.moveForward(score);
        } else {
            int randomNumber = (int)(otherObstacle.getLocation()).x + 15 +randomGenerator.nextInt(15);
            int randomHeight = randomGenerator.nextInt(2);
            int height=0;
            if(randomHeight==0){
                height=2;
            }
            else{
                height=8;
            }
            Vector3f newPosition = new Vector3f(randomNumber, height, 0);
            this.setLocation(newPosition);
        }
    }
}
