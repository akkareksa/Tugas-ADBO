/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
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
    public static final String RUN_TOP = "RunTop";
    public static final String RUN_BASE = "RunBase";
    public static final String JUMP_LOOP = "JumpLoop";
    public static final String IDLE_BASE = "IdleBase";
    private AnimControl animControl;
    private AnimChannel torsoChannel;
    private AnimChannel feetChannel;
//    
//    public Trex(Spatial spatial){
//        this.spatial = spatial;
//    }
    
    public void jump(BulletAppState bulletAppState,Spatial cactus,Spatial pteradacty,Spatial terrain, AppStateManager a, RigidBodyControl terrainPhysicsNode,AssetManager assetManager) {
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(false);
        a.attach(bulletAppState);
        spatial = assetManager.loadModel("Models/Dino.j3o");
        
       // spatial.getLocalRotation().fromAngleAxis(-1.5708f, Vector3f.UNIT_Y);

       //spatial.setLocalTranslation(spatial.getLocalTranslation().add(new Vector3f(0f,10f,0f)));
        CollisionShape sceneShape
                = CollisionShapeFactory.createMeshShape(terrain);
        terrainPhysicsNode = new RigidBodyControl(sceneShape,0);
        terrain.addControl(terrainPhysicsNode);
        BoundingBox trexBB = (BoundingBox)spatial.getWorldBound();
//        BoundingBox cactusBB = (BoundingBox)cactus.getWorldBound();
//        BoundingBox pteradactyBB = (BoundingBox)pteradacty.getWorldBound();
        CapsuleCollisionShape capsuleTrex = new CapsuleCollisionShape(trexBB.getZExtent(), trexBB.getYExtent());
//        CapsuleCollisionShape capsuleCactus = new CapsuleCollisionShape(cactusBB.getZExtent(),cactusBB.getYExtent());
//        CapsuleCollisionShape capsulePteradacty = new CapsuleCollisionShape(pteradactyBB.getZExtent(),pteradactyBB.getYExtent());
//        CharacterControl cactusControl = new CharacterControl(capsuleCactus, 1f);
//        CharacterControl pteradactyControl = new CharacterControl(capsulePteradacty, 1f);
        playerControl = new CharacterControl(capsuleTrex, 1f);
        spatial.addControl(playerControl);
        playerControl.setJumpSpeed(50);
        playerControl.setFallSpeed(90);
        playerControl.setGravity(90);
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
        if (checkAnimControl()) {
            if(playerControl.onGround()){
                if (!RUN_TOP.equals(torsoChannel.getAnimationName())) {
                    torsoChannel.setAnim(RUN_TOP);
                }
                if (!RUN_BASE.equals(feetChannel.getAnimationName())) {
                    feetChannel.setAnim(RUN_BASE);
                }
            }
            else{
                if (!JUMP_LOOP.equals(torsoChannel.getAnimationName())) {
                    torsoChannel.setAnim(JUMP_LOOP);
                }
                if (!JUMP_LOOP.equals(feetChannel.getAnimationName())) {
                    feetChannel.setAnim(JUMP_LOOP);
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean checkAnimControl() {
        AnimControl control = spatial.getControl(AnimControl.class);
        if (control != animControl) {
            this.animControl = control;
            if (animControl != null) {
                torsoChannel = animControl.createChannel();
                feetChannel = animControl.createChannel();
            }
        }
        return animControl != null;
    }
    public Vector3f getTranslation(){
        return this.spatial.getLocalTranslation();
    }
    public void removeControl(){
        this.spatial.removeControl(playerControl);
    }
}