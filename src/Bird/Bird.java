/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author ASUS
 */
public class Bird extends Obstacle{
 

    
    public Bird(Spatial bird){
        super(bird);
    }
    
    @Override
    public void moveForward(float score){
        Vector3f v = this.spatial.getLocalTranslation();
        this.spatial.setLocalTranslation(v.x-score*0.01f, v.y, v.z);
    }
    
    @Override
    public Vector3f getLocation(){
        return spatial.getLocalTranslation();
    }
    
    @Override
    public Spatial getSpatial(){
        return this.spatial;
    }
    
}