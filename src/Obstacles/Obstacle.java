/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obstacles;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public abstract class Obstacle {

    private Spatial objectSpatial;
    private Random randomGenerator;
    private int temporaryRespawnX;

    public Obstacle(Spatial objectSpatial) {
        this.objectSpatial = objectSpatial;
        randomGenerator = new Random();
    }

    public Vector3f getLocation() {
        return objectSpatial.getLocalTranslation();
    }

    private void moveForward(float score) {
        Vector3f v = this.objectSpatial.getLocalTranslation();
        this.objectSpatial.setLocalTranslation(v.x - score * 0.01f, v.y, v.z);
    }

    public Spatial getSpatial() {
        return this.objectSpatial;
    }

    public void setLocation(Vector3f location) {
        this.objectSpatial.setLocalTranslation(location);
    }

    public void respawnFirst(float score) {
        if (this.getLocation().x > -30) {
            this.moveForward(score);
        } else {
            int randomNum = randomGenerator.nextInt(10);
            Vector3f newPosition = new Vector3f(randomNum, 0, 0);
            this.setLocation(newPosition);
        }
    }

    public void respawnSecond(float score,Obstacle otherObstacle) {
        if (this.getLocation().x > -30) {
            this.moveForward(score);
        } else {
            int randomNumber = (int)(otherObstacle.getLocation()).x + 10 +randomGenerator.nextInt(15);
            Vector3f newPosition = new Vector3f(randomNumber, 0, 0);
            this.setLocation(newPosition);
        }
    }
}