/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enemies;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author Kevin Gerry Naofal
 */
public abstract class Obstacle {

    protected Spatial objectSpatial;
    private Random randomGenerator;
    private int temporaryRespawnX;

    /**
     * Contructor untuk abstract class Obstacle
     *
     */
    public Obstacle() {

        randomGenerator = new Random();
    }

    /**
     * method ini akan membuat objek spatial maju
     *
     * @param score sebuah float untuk menentukan kecepatan dari objek spatial
     */
    public void moveForward(float score) {
        Vector3f v = this.objectSpatial.getLocalTranslation();
        this.objectSpatial.setLocalTranslation(v.x - score * 0.01f, v.y, v.z);
    }

    /**
     * method abstract ini akan di override di kelas turunan Obstacle yaitu
     * cactus dan pteradactyl
     *
     * @param assetManager
     */
    public abstract void setSpatial(AssetManager assetManager);

    /**
     * method ini mengembalikan Spatial objek yang merepresentasikan bentuk
     * fisik dari objek tersebut
     *
     * @return Spatial
     */
    public Spatial getSpatial() {
        return this.objectSpatial;
    }


    /**
     * method ini digunakan untuk menentukan posisi baru untuk objek spatial;
     * @param location sebuah vector3f untuk mengisi lokasi baru 
     */
    public void setLocation(Vector3f location) {
        this.objectSpatial.setLocalTranslation(location);
    }
    
    /**
     * method ini digunakan untuk mendapatkan posisi dari objek spatial;
     *
     * @return vector3f posisi spatial;
     */
    public Vector3f getLocation() {
        return objectSpatial.getLocalTranslation();
    }
    
    /**
     * method ini akan membuat obstacle pertama untuk muncul kembali
     * @param score sebuah float untuk menentukan kecepatan dari objek spatial
     */

    public void respawnFirst(float score) {
        if (this.getLocation().x > -5) {
            this.moveForward(score);
        } else {
            int randomNum = 45 + randomGenerator.nextInt(10);
            Vector3f newPosition = new Vector3f(randomNum, 1, 0);
            this.setLocation(newPosition);
        }
    }
    
    /**
     * method ini akan membuat obstacle kedua untuk muncul kembali
     * @param score sebuah float untuk menentukan kecepatan dari objek spatial
     */

    public void respawnSecond(float score, Obstacle otherObstacle) {
        if (this.getLocation().x > -5) {
            this.moveForward(score);
        } else {
            int randomNumber = (int) (otherObstacle.getLocation()).x + 15 + randomGenerator.nextInt(15);
            int randomHeight = randomGenerator.nextInt(3);
            float height = 0;
            if (randomHeight == 0) {
                height = 2;
            } else {
                height = 8;
            }
            Vector3f newPosition = new Vector3f(randomNumber, height, 0);
            this.setLocation(newPosition);
        }
    }
}
