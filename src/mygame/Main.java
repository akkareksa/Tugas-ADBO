package mygame;

import Enemies.Cactus;
import Enemies.Obstacle;
import Enemies.Pteradactyl;
import Manager.EventManager;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.GammaCorrectionFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication implements ActionListener {

    private Spatial terrain, quixote;
    private Trex trex;
    private RigidBodyControl terrainPhysicsNode;
    private Cactus cactus;
    private Pteradactyl pteradactyl;
    private Obstacle[] arrOfObstacle;
    private EventManager eventer;
    private BulletAppState bulletAppState;
    private AbstractAppState abstractAppState;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        abstractAppState = new AbstractAppState();
        abstractAppState.setEnabled(true);
        stateManager.attach(abstractAppState);
        flyCam.setMoveSpeed(100);
        setUpKeys();
        flyCam.setEnabled(false);
        Vector3f v = new Vector3f(12f, 5f, 30f);
        cam.setLocation(v);
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        terrain = assetManager.loadModel("Scenes/newScene.j3o");
        //quixote = assetManager.loadModel("Models/Dino.j3o");
        terrain.setLocalScale(2f);
        trex = new Trex();
        rootNode.attachChild(terrain);
        
        
         Spatial cactusSpatial = assetManager.loadModel("/Models/model/model.j3o");  
        Spatial pteradactylSpatial = assetManager.loadModel("Models/Rham-Phorynchus/Rham-Phorynchus.j3o");
        trex.jump(bulletAppState,cactusSpatial,pteradactylSpatial,terrain, stateManager, terrainPhysicsNode,assetManager);
        rootNode.attachChild(trex.getTrex());
        quixote = trex.getTrex();
        
        pteradactylSpatial.getLocalRotation().fromAngleAxis(-1.5708f, Vector3f.UNIT_Y);
        cactusSpatial.getLocalRotation().fromAngleAxis(0.785398f, Vector3f.UNIT_Y);
       
        cactus = new Cactus(cactusSpatial);
        pteradactyl = new Pteradactyl(pteradactylSpatial);
        cactus.setLocation(new Vector3f(25f, 1, 0));
        pteradactyl.setLocation(new Vector3f(45f, 2, 0));
        
        rootNode.attachChild(cactus.getSpatial());
        rootNode.attachChild(pteradactyl.getSpatial());

        arrOfObstacle = new Obstacle[2];
        arrOfObstacle[0] = cactus;
        arrOfObstacle[1] = pteradactyl;
        eventer = new EventManager(arrOfObstacle);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(abstractAppState.isEnabled()){
            eventer.newSpawnStyle();
            CollisionResults collide = new CollisionResults();
            for (int i = 0; i < arrOfObstacle.length; i++) {
                BoundingBox bv =(BoundingBox) arrOfObstacle[i].getSpatial().getWorldBound();
                quixote.collideWith(bv, collide);

                if (collide.size() > 0) {
                    abstractAppState.setEnabled(false);
                } 
            }
        }
        else{
            trex.removeControl();
        }
    }

    private void setUpKeys() {
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Space");
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addListener(this, "Up");
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Space")||binding.equals("Up")) {
            trex.getPlayerControl().jump();
        }
    }

}
