/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obstacles;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Kevin
 */
public abstract class Obstacle {
    private Spatial objectSpatial;
    
    public Obstacle(Spatial objectSpatial){
        this.objectSpatial=objectSpatial;
    }
    
    public Vector3f getLocation(){
        return objectSpatial.getLocalTranslation();
    }
    
    public void moveForward(float score){
        Vector3f v = this.objectSpatial.getLocalTranslation();
        this.objectSpatial.setLocalTranslation(v.x-score*0.01f,v.y,v.z);
    }
    
    public Spatial getSpatial(){
        return this.objectSpatial;
    }
    
    public void setLocation(Vector3f location){
        this.objectSpatial.setLocalTranslation(location);
    }
}
