/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enemies;

import com.jme3.asset.AssetManager;

/**
 *
 * @author Kevin
 */
public class CactusJeffrey extends Cactus {
    
    
    @Override
    public void setSpatial(AssetManager assetManager){
        this.cactus=assetManager.loadModel("Models/Models/Jeffrey/source/creepy_cactus/creepy_cactus.j3o");
    }
    
}
