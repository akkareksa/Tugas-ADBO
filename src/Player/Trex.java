/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

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

public class Trex extends AbstractControl {
    private BulletAppState bulletAppState;
    private CharacterControl playerControl;
    public static final String RUN_TOP = "RunTop";
    public static final String RUN_BASE = "RunBase";
    public static final String JUMP_LOOP = "JumpLoop";
    public static final String IDLE_BASE = "IdleBase";
    private AnimControl animControl;
    private AnimChannel torsoChannel;
    private AnimChannel feetChannel;
    
    
    public void jump(Spatial terrain, AppStateManager a, RigidBodyControl terrainPhysicsNode,AssetManager assetManager) {
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(false);
        a.attach(bulletAppState);
        spatial = assetManager.loadModel("Models/Player/Quixote.j3o");
        spatial.rotate(0, 1.5f, 0);
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
        controlUpdate(y);
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
}