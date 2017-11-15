package mygame;


import Enemies.Cactus;
import Enemies.Obstacle;
import Enemies.Pteradactyl;
import Manager.EventManager;
import Player.*;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
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
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;


/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * 
 *
 * @author Kevin
 *         Naofal
 *         Gerry
 *         Firman
 *         Andrianto
 * 
 */


public class Main extends SimpleApplication implements ActionListener {

    private Spatial terrain, quixote;
    private Trex trex;
    private RigidBodyControl terrainPhysicsNode;
    private Cactus cactus;
    private Pteradactyl pteradactyl;
    private Obstacle[] arrOfObstacle;
    private EventManager eventer;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(100);
        setUpKeys();
        flyCam.setEnabled(true);
        Vector3f v = new Vector3f(14f, 5f, 40f);
        cam.setLocation(v);
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        terrain = assetManager.loadModel("Scenes/newScene.j3o");
        terrain.setLocalScale(2f);
        trex = new Trex();
        rootNode.attachChild(terrain);
        
        trex.jump(terrain, stateManager, terrainPhysicsNode,assetManager);
        rootNode.attachChild(trex.getTrex());

        cactus = new Cactus();
        pteradactyl = new Pteradactyl();
        cactus.setSpatial(assetManager);
        pteradactyl.setSpatial(assetManager);
        // Spatial cactusSpatial = assetManager.loadModel("Models/model/model.j3o");  
       // Spatial pteradactylSpatial = assetManager.loadModel("Models/Rham-Phorynchus/Rham-Phorynchus.j3o");
        
       // pteradactylSpatial.getLocalRotation().fromAngleAxis(-1.5708f, Vector3f.UNIT_Y);
       // cactusSpatial.getLocalRotation().fromAngleAxis(0.785398f, Vector3f.UNIT_Y);
       
      //  cactus = new Cactus(cactusSpatial);
       // pteradactyl = new Pteradactyl(pteradactylSpatial);
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
        eventer.newSpawnStyle();
        
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