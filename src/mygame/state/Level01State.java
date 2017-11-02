/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Aan
 */
public class Level01State extends AbstractAppState {
    private final Node rootNode;
    private final Node localRootNode= new Node("Level 1");
    private final AssetManager assetManager;
    private final InputManager inputManager;
    private BulletAppState bulletAppState;
    private Spatial player;
    private CharacterControl playerControl;
    private final FlyByCamera flyByCamera;
    private final Camera camera;
    private ChaseCamera chaseCam;
    
    private final Vector3f playerWalkDirection=Vector3f.ZERO;
    private boolean left=false, right=false, up=false, down=false;
    
    public Level01State(SimpleApplication app){
        rootNode=app.getRootNode();
        assetManager=app.getAssetManager();
        inputManager=app.getInputManager();
        flyByCamera=app.getFlyByCamera();
        camera=app.getCamera();
        
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
       
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);
        
        rootNode.attachChild(localRootNode);
        
        //Spatial player1=assetManager.loadModel("Models/Lt_boy/Lt_boy.j3o");
        //localRootNode.attachChild(player1);
        
        Spatial scene = assetManager.loadModel("Scenes/Level1.j3o");
        localRootNode.attachChild(scene);
        
        // load floor
        Spatial floor = localRootNode.getChild("Floor");
        bulletAppState.getPhysicsSpace().add(floor.getControl(RigidBodyControl.class));
        
        // load player
        player = localRootNode.getChild("Player");
        BoundingBox boundingBox = (BoundingBox)player.getWorldBound();
        float radius=boundingBox.getXExtent();
        float height=boundingBox.getYExtent();
        
        CapsuleCollisionShape playerShape = new CapsuleCollisionShape(radius,height);   
        
        playerControl=new CharacterControl(playerShape, 1.0f);
        player.addControl(playerControl);
        
        bulletAppState.getPhysicsSpace().add(playerControl);
        
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));       
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));       
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));      
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));      
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));    
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(actionListener, "Up");
        inputManager.addListener(actionListener, "Down");
        inputManager.addListener(actionListener, "Left");
        inputManager.addListener(actionListener, "Right");
        inputManager.addListener(actionListener, "Jump");   
        
        flyByCamera.setEnabled(false);
        chaseCam=new ChaseCamera(camera,player, inputManager);
    }
    
    private final ActionListener actionListener=new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("Pause") && !isPressed){
                setEnabled(!isEnabled());
            }
            else if(name.equals("Up")){
                up=isPressed;
            }             
            else if(name.equals("Down")){
                down=isPressed;
            }             
            else if(name.equals("Left")){
                left=isPressed;
            }             
            else if(name.equals("Right")){
                right=isPressed;
            }             
            else if(name.equals("Jump")){
                playerControl.jump();
            }                            
        }
        
    };
    
    @Override
    public void cleanup(){
        rootNode.detachChild(localRootNode);
        
        super.cleanup();
    }
    
    /**
     * Untuk membuat benda bergerak
     * @param tpf 
     */
    @Override
    public void update(float tpf){
        Vector3f cameraDir = camera.getDirection().clone();
        Vector3f cameraLeft = camera.getLeft().clone();
        cameraDir.y=0;
        cameraLeft.y=0;
        
        cameraDir.normalizeLocal();
        cameraLeft.normalizeLocal();
        
        playerWalkDirection.set(0,0,0);
        
        if(left) playerWalkDirection.addLocal(cameraLeft);
        if(right) playerWalkDirection.addLocal(cameraLeft.negate());
        if(up) playerWalkDirection.addLocal(cameraDir);
        if(down) playerWalkDirection.addLocal(cameraDir.negate());
        
        if(player!=null){
            playerWalkDirection.multLocal(10f).multLocal(tpf);
            playerControl.setWalkDirection(playerWalkDirection);
        }
        
    }
}
