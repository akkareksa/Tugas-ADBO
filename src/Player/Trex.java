/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.Animation;
import com.jme3.animation.SpatialTrack;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsCharacter;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author naofa
 */
public class Trex extends AbstractControl {
    private CharacterControl playerControl;
    private AnimControl animControl;
    private AnimChannel channel;
    private Animation test;
    
    /**
     * method ini berguna untuk menginisialisasi trex di game
     * spatial diload disini dan menginisialisasikan collision shape
     * agar mendeteksi collision
     * @param bulletAppState
     * @param cactus
     * @param pteradacty
     * @param terrain
     * @param a
     * @param terrainPhysicsNode
     * @param assetManager 
     */
    public void initializeTrex(BulletAppState bulletAppState,Spatial cactus,Spatial pteradacty,Spatial terrain, AppStateManager a, RigidBodyControl terrainPhysicsNode,AssetManager assetManager) {
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(false);
        a.attach(bulletAppState);
        spatial = assetManager.loadModel("Models/Dino/Dino.j3o");
        CollisionShape sceneShape
                = CollisionShapeFactory.createMeshShape(terrain);
        terrainPhysicsNode = new RigidBodyControl(sceneShape,0);
        terrain.addControl(terrainPhysicsNode);
        BoundingBox trexBB = (BoundingBox)spatial.getWorldBound();
        CapsuleCollisionShape capsuleTrex = new CapsuleCollisionShape(trexBB.getZExtent(), trexBB.getYExtent());
        playerControl = new CharacterControl(capsuleTrex, 1f);
        spatial.addControl(playerControl);
        playerControl.setJumpSpeed(30);
        playerControl.setFallSpeed(80);
        playerControl.setGravity(80);
        playerControl.setPhysicsLocation(new Vector3f(0, 10, 0));
        bulletAppState.getPhysicsSpace().add(playerControl);
        bulletAppState.getPhysicsSpace().add(terrainPhysicsNode);
        controlUpdate(0);
    }
    /**
     * getter dari spatial
     * @return spatial
     */
    public Spatial getTrex() {
        return this.spatial;
    }
    
    /**
     * getter Player control
     * @return playerControl
     */
    public CharacterControl getPlayerControl(){
        return this.playerControl;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * method ini berguna untuk meremove character control
     * dari spatial player
     */
    public void removeControl(){
        this.spatial.removeControl(playerControl);
    }
    
    /**
     * method ini berguna untuk add character control
     * ke spatial player
     */
    public void addControl(){
        this.spatial.addControl(playerControl);
    }
    
}