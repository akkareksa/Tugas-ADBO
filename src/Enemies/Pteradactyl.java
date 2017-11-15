/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enemies;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Kevin
 *         Naofal
 *         Gerry
 *         Firman
 *         Andrianto
 * 
 */
public class Pteradactyl extends Obstacle {
     private Spatial pteradactyl;

    
    public Pteradactyl(){
        this.pteradactyl=pteradactyl;
    }
    
    
     @Override
    public void setSpatial(AssetManager assetManager){
        this.pteradactyl=assetManager.loadModel("Models/Rham-Phorynchus/Rham-Phorynchus.j3o");
    }
    
     @Override
    public Spatial getSpatial(){
        return this.pteradactyl;
    }
    
     @Override
    public void setLocation(Vector3f location){
        this.pteradactyl.setLocalTranslation(location);
    }
    
    @Override
    public Vector3f getLocation() {
        return this.pteradactyl.getLocalTranslation();
    }
    
     @Override
    public void moveForward(float score) {
        Vector3f v = this.pteradactyl.getLocalTranslation();
        this.pteradactyl.setLocalTranslation(v.x - score * 0.01f, v.y, v.z);
    }
}
