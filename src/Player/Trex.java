/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

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
import java.util.HashMap;

/**
 *
 * @author naofa
 */
public class Trex extends AbstractControl {
    private CharacterControl playerControl;
    private AnimControl animControl;
    private AnimChannel channel;
    private Animation test;
//    
//    public Trex(Spatial spatial){
//        this.spatial = spatial;
//    }
    
    public void jump(BulletAppState bulletAppState,Spatial cactus,Spatial pteradacty,Spatial terrain, AppStateManager a, RigidBodyControl terrainPhysicsNode,AssetManager assetManager) {
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
        playerControl.setJumpSpeed(40);
        playerControl.setFallSpeed(80);
        playerControl.setGravity(80);
        playerControl.setPhysicsLocation(new Vector3f(0, 10, 0));
        bulletAppState.getPhysicsSpace().add(playerControl);
        bulletAppState.getPhysicsSpace().add(terrainPhysicsNode);
        controlUpdate(0);
    }

    public Spatial getTrex() {
        return this.spatial;
    }
    
    public void move(){
        spatial.move(0.1f, 0, 0);
    }
    
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
    
    public void removeControl(){
        this.spatial.removeControl(playerControl);
    }
    
    public void addControl(){
        this.spatial.addControl(playerControl);
    }
//    public void anim(){
//        AnimControl control = spatial.getControl(AnimControl.class);
//        this.animControl = control;
//        this.channel = animControl.createChannel();
//        this.channel.setAnim("Run");
//    }
}