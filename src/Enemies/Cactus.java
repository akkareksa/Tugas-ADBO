
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
public class Cactus extends Obstacle{
    
    protected Spatial cactus;
    
    
    public Cactus(){
        this.cactus=cactus;
    }
    
    @Override
    public void setSpatial(AssetManager assetManager){
        this.cactus=assetManager.loadModel("Models/model/model.j3o");
        this.cactus.getLocalRotation().fromAngleAxis(-3.14159f, Vector3f.UNIT_Y);
    }
   
    @Override
    public Spatial getSpatial(){
        return this.cactus;
    }
    
     @Override
    public void setLocation(Vector3f location){
        this.cactus.setLocalTranslation(location);
    }
    
    @Override
    public Vector3f getLocation() {
        return this.cactus.getLocalTranslation();
    }
    
    
    
    @Override
    public void moveForward(float score) {
        Vector3f v = this.cactus.getLocalTranslation();
        this.cactus.setLocalTranslation(v.x - score * 0.01f, v.y, v.z);
    }
}

