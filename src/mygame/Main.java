package mygame;

import Player.Trex;
import Enemies.*;
import Manager.EventManager;
import Player.*;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
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
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication implements ActionListener {
    private boolean status=true;
    private AudioNode audio, dead;
    private Spatial terrain, quixote;
    private Trex trex;
    private RigidBodyControl terrainPhysicsNode;
    private Cactus cactus;
    private Pteradactyl pteradactyl;
    private Obstacle[] arrOfObstacle;
    private EventManager eventer;
    private BulletAppState bulletAppState;
    private AbstractAppState abstractAppState;
    private int score;
    BitmapText scoreText, highScore;
    float temp;
    private int highscoreTemp = 0;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        audio = new AudioNode(assetManager, "Sounds/effect/Jump.ogg", DataType.Buffer);
        audio.setPositional(false);
        audio.setLooping(false);
        audio.setVolume(2);
        rootNode.attachChild(audio);
        dead = new AudioNode(assetManager, "Sounds/effect/t_g_life.ogg", DataType.Buffer);
        dead.setPositional(false);
        dead.setLooping(false);
        dead.setVolume(5);
        rootNode.attachChild(dead);
        abstractAppState = new AbstractAppState();
        abstractAppState.setEnabled(true);
        stateManager.attach(abstractAppState);
        flyCam.setMoveSpeed(100);
        setUpKeys();
        flyCam.setEnabled(false);
        Vector3f v = new Vector3f(9f, 5f, 25f);
        cam.setLocation(v);

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
        trex.initializeTrex(bulletAppState, cactusSpatial, pteradactylSpatial, terrain, stateManager, terrainPhysicsNode, assetManager);
        rootNode.attachChild(trex.getTrex());
        quixote = trex.getTrex();

        cactus = new CactusGangsta();
        pteradactyl = new Pteradactyl();
        cactus.setSpatial(assetManager);
        pteradactyl.setSpatial(assetManager);
        cactus.setLocation(new Vector3f(25f, 1, 0));
        pteradactyl.setLocation(new Vector3f(45f, 2, 0));
        // cactus.setLocation(new Vector3f(25f, 1, 0));
        //  pteradactyl.setLocation(new Vector3f(45f, 2, 0));

        rootNode.attachChild(cactus.getSpatial());
        rootNode.attachChild(pteradactyl.getSpatial());

        arrOfObstacle = new Obstacle[2];
        arrOfObstacle[0] = cactus;
        arrOfObstacle[1] = pteradactyl;

        eventer = new EventManager(arrOfObstacle);
        scoreText = new BitmapText(guiFont, false);
        BitmapText s = new BitmapText(guiFont);
        scoreText.setText("0");
        scoreText.setColor(ColorRGBA.Red);
        scoreText.setSize(32);
        scoreText.setStyle(0, 0, 5);
        s.setText("Score:");
        s.setSize(32);
        s.setStyle(0, 0, 5);
        guiNode.attachChild(scoreText);
        highScore = new BitmapText(guiFont, false);
        highScore.setColor(ColorRGBA.Red);
        highScore.setSize(32);
        scoreText.setStyle(0, 0, 100);
        highScore.setText("High Score: " + highscoreTemp);
        guiNode.attachChild(highScore);
        scoreText.setLocalTranslation(cam.getWidth() - 50,
                cam.getHeight(), 0.0f);
        highScore.setLocalTranslation(cam.getWidth() - 500,
                cam.getHeight(), 1.0f);
        s.setLocalTranslation(cam.getWidth() - 200, cam.getHeight(), 0.0f);
        guiNode.attachChild(s);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (abstractAppState.isEnabled()) {
            eventer.newSpawnStyle();
            if (temp >= 1) {
                score++;
                scoreText.setText("" + score);
                temp = 0;
            } else {
                temp += tpf * 10;
            }
            CollisionResults collide = new CollisionResults();
            for (int i = 0; i < arrOfObstacle.length; i++) {
                BoundingBox bv = (BoundingBox) arrOfObstacle[i].getSpatial().getWorldBound();
                quixote.collideWith(bv, collide);

                if (collide.size() > 0) {
                    abstractAppState.setEnabled(false);
                    status=false;
                }
            }
        } else {
            if(!status){
                dead.setVolume(5);
                dead.play();
                status=true;
            }
            if (highscoreTemp <= Integer.parseInt(scoreText.getText())) {
                trex.removeControl();
                highscoreTemp = Integer.parseInt(scoreText.getText());
                highScore.setText("High Score: " + highscoreTemp);
            }
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
        if (abstractAppState.isEnabled()) {
            if (binding.equals("Space") || binding.equals("Up")) {
                trex.getPlayerControl().jump();
                audio.play();
            }
        } else {
            if (binding.equals("Space") || binding.equals("Up")) {
                status = true;
                dead.stop();
                score = 0;
                cactus.setLocation(new Vector3f(35f, 1, 0));
                pteradactyl.setLocation(new Vector3f(55f, 2, 0));
                abstractAppState.setEnabled(true);
                trex.addControl();
            }
        }
    }

}