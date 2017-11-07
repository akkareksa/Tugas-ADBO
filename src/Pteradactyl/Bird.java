/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pteradactyl;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author ASUS
 */


public class Bird extends Obstacle{
 
private final Random rand;
    
    public Bird(Spatial bird){
        super(bird);
        rand = new Random();
    }
    
    @Override
    public void moveForward(float score){
        Vector3f v = this.spatial.getLocalTranslation();
        this.spatial.setLocalTranslation(v.x, v.y, v.z+score*0.01f);
    }
    
    @Override
    public Vector3f getLocation(){
        return spatial.getLocalTranslation();
    }
    
    @Override
    public Spatial getSpatial(){
        return this.spatial;
    }
    
    public void respawn(){
        float temp = 1 + rand.nextInt(10);

        Vector3f tempatBaru = new Vector3f(0, temp, 0);

        if (this.getLocation().z < 30) {
            this.moveForward(1);

        } else {
            this.setLocation(tempatBaru);

        }
    }
    
}
