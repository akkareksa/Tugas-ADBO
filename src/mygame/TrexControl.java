/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;

/**
 *
 * @author naofa
 */
public class TrexControl {
    
    private BulletAppState bulletAppState;
    private CharacterControl playerControl;
    
    public void setThePlayerControl(Spatial terrain, AppStateManager a, RigidBodyControl terrainPhysicsNode,AssetManager assetManager,Spatial spatial){
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(false);
        a.attach(bulletAppState);
        CollisionShape sceneShape
                = CollisionShapeFactory.createMeshShape(terrain);
        terrainPhysicsNode = new RigidBodyControl(sceneShape,0);
        terrain.addControl(terrainPhysicsNode);
        BoundingBox bb = (BoundingBox)spatial.getWorldBound();
        float x= bb.getXExtent();
        float y = bb.getYExtent();
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(x,y);
        
        playerControl = new CharacterControl(capsule,1.0f);
        spatial.addControl(playerControl);
        playerControl.setJumpSpeed(50);
        playerControl.setFallSpeed(50);
        playerControl.setGravity(90);
        bulletAppState.getPhysicsSpace().add(playerControl);
        bulletAppState.getPhysicsSpace().add(terrainPhysicsNode);
    }
    
    public CharacterControl getPlayerControl(){
        return this.playerControl;
    }
}
