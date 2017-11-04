package mygame;

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

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {
private Cactus kotak;
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
        /**
         * A white ambient light source.
         */
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        Spatial cactus = assetManager.loadModel("/Models/Obstacle/model.j3o");

        Spatial gege = (Spatial) geom;
        kotak = new Cactus(cactus);

        rootNode.attachChild(kotak.getSpatial());
        //kotak.moveForward(100);
        //kotak.moveForward(100);
        //kotak.moveForward(100);
        //kotak.moveForward(100);
    }

    @Override
    public void simpleUpdate(float tpf) {
        kotak.respawn(0.9f);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
