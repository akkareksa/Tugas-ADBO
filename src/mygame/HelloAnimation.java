package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */

public class HelloAnimation extends SimpleApplication
        implements AnimEventListener {

    private boolean up=false,down=false;
    private CharacterControl playerControl;
    private AnimChannel channel;
    private AnimControl control;
    Node player;

    public static void main(String[] args) {
        HelloAnimation app = new HelloAnimation();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        initKeys();
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);
        player = (Node) assetManager.loadModel("Models/Dino/Dino.j3o");
        player.setLocalScale(0.5f);
        rootNode.attachChild(player);
        control = player.getControl(AnimControl.class);
//        control.addListener(this);
//        channel = control.createChannel();
//        channel.setAnim("stand");
    }

//    private void jump(){
//        channel.setAnim("Jump");
//        channel.setLoopMode(LoopMode.DontLoop);
//        channel.setSpeed(1.5f);
//    }
//    
//    private void run(){
//        channel.setAnim("Run");
//        channel.setLoopMode(LoopMode.DontLoop);
//        channel.setSpeed(1.5f);
//        up=true;
//    }
//    
//    private void crouch(){
//        channel.setAnim("Crouch");
//        channel.setLoopMode(LoopMode.DontLoop);
//        channel.setSpeed(1.5f);
//        down=true;
//    }
    
    private final ActionListener actionListener=new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("Up")){
                up=isPressed;
            }
            else if(name.equals("Crouch")){
                down=isPressed;
            }
            else if(name.equals("Jump")){
                playerControl.jump();
            }                            
        }
        
    };
    
    /**
     * Custom Keybinding: Map named actions to inputs.
     */
    private void initKeys() {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));        
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Crouch", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(actionListener, "Up");
        inputManager.addListener(actionListener, "Jump");   
        inputManager.addListener(actionListener, "Crouch");
    }
//    private ActionListener actionListener = new ActionListener() {
//        public void onAction(String name, boolean keyPressed, float tpf) {
//            if (name.equals("Walk") && !keyPressed) {
//                if (!channel.getAnimationName().equals("Walk")) {
//                    channel.setAnim("Walk", 0.50f);
//                    channel.setLoopMode(LoopMode.Loop);
//                }
//            }
//        }
//    };

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if(animName.equals("Jump")){
            channel.setAnim("Jump", 1.5f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
        else if(animName.equals("Up")){
            channel.setAnim("Up", 1.5f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
        else if(animName.equals("Crouch")){
            channel.setAnim("Crouch", 1.5f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(.5f);
        }
    }
    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

}
