package mygame;

import Player.Trex;
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
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import Obstacles.*;
import Manager.EventManager;
import com.jme3.math.Vector3f;

/**
 * Menggabungkan kelas Trex dan obstacle
 *
 * @author  Kevin
 *          Gerry
 *          Naofal
 *          Andrianto
 *          Firman
 */
public class Main extends SimpleApplication implements ActionListener {

    private Spatial terrain;
    private Trex trex;
    private RigidBodyControl terrainPhysicsNode;
    private Cactus kotak, kotak2;
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
        trex.jump(terrain, stateManager, terrainPhysicsNode, assetManager);
        rootNode.attachChild(trex.getTrex());

        Spatial cactus = assetManager.loadModel("/Models/Obstacle/model.j3o");
        kotak = new Cactus(cactus);
        kotak2 = new Cactus(cactus);
        kotak.setLocation(new Vector3f(0, 0, 0));
        kotak2.setLocation(new Vector3f(5f, 0, 0));
        
        rootNode.attachChild(kotak.getSpatial());
        rootNode.attachChild(kotak2.getSpatial());

        arrOfObstacle = new Obstacle[2];
        arrOfObstacle[0] = kotak;
        arrOfObstacle[1] = kotak2;
        
        eventer = new EventManager(arrOfObstacle);
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        trex.move();
        eventer.newSpawnStyle();
    }

    private void setUpKeys() {

        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Space");
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Space")) {
            trex.getPlayerControl().jump();
        }
    }

}
