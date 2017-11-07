package mygame;

import Manager.EventManager;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import Obstacles.*;
import com.jme3.light.AmbientLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author Kevin & Gerry
 */
public class Main extends SimpleApplication {

    private Cactus kotak, kotak2, kotak3, kotak4;
    private Obstacle[] arrOfObstacle;
    private EventManager eventer;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        Spatial cactus = assetManager.loadModel("/Models/Obstacle/model.j3o");

        kotak = new Cactus(cactus);
        kotak2 = new Cactus(geom);
        
        kotak.setLocation(new Vector3f(0, 0, 0));
        kotak2.setLocation(new Vector3f(5f, 0, 0));
        rootNode.attachChild(kotak.getSpatial());
        rootNode.attachChild(kotak2.getSpatial());

        
        arrOfObstacle = new Obstacle[4];
        arrOfObstacle[0] = kotak;
        arrOfObstacle[1] = kotak2;

        //arrOfObstacle = {kotak,kotak2,kotak3,kotak};
        eventer = new EventManager(arrOfObstacle);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //kotak.respawn(0.9f);
        //eventer.spawnModelsinRandomxVector();
        eventer.newSpawnStyle();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code 
        
    }
}
