/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pteradactyl;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author ASUS
 */
public abstract class Obstacle {
    
    protected Spatial spatial;
    
    public Obstacle(Spatial spatial){
        this.spatial=spatial;
    }
    
    public void moveForward(float score){
        Vector3f v = this.spatial.getLocalTranslation();
        this.spatial.setLocalTranslation(v.x, v.y, v.z+score*0.01f);
    }
    
    public Vector3f getLocation(){
        return spatial.getLocalTranslation();
    }
    
    public void setLocation(Vector3f vektor){
        spatial.setLocalTranslation(vektor);
    }
    
    public Spatial getSpatial(){
        return this.spatial;
    }
}