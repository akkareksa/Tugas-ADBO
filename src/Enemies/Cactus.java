
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
    
    /**
     * method ini digunakan untuk memberikan fisik untuk objek spatial cactus
     * @param assetManager 
     */
    
    @Override
    public void setSpatial(AssetManager assetManager){
        this.cactus=assetManager.loadModel("Models/model/model.j3o");
        this.cactus.getLocalRotation().fromAngleAxis(-3.14159f, Vector3f.UNIT_Y);
    }
   
    
    /**
     * method ini mengembalikan Spatial objek yang merepresentasikan bentuk fisik dari pteradactyl
     *
     * @return Spatial
     */
    @Override
    public Spatial getSpatial(){
        return this.cactus;
    }
    /**
     * method ini digunakan untuk menentukan posisi baru untuk objek spatial pteradactyl;
     * @param location sebuah vector3f untuk mengisi lokasi baru 
     */
     @Override
    public void setLocation(Vector3f location){
        this.cactus.setLocalTranslation(location);
    }
    
    /**
     * method ini digunakan untuk mendapatkan posisi dari objek spatial pteradactyl;
     *
     * @return vector3f posisi spatial;
     */
    @Override
    public Vector3f getLocation() {
        return this.cactus.getLocalTranslation();
    }
    
    /**
     * method ini akan membuat objek spatial pteradactyl maju
     *
     * @param score sebuah float untuk menentukan kecepatan dari objek spatial
     */
    
    @Override
    public void moveForward(float score) {
        Vector3f v = this.cactus.getLocalTranslation();
        this.cactus.setLocalTranslation(v.x - score * 0.01f, v.y, v.z);
    }
}
