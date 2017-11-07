package mygame;

import Pteradactyl.*;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.light.AmbientLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Sample 5 - how to map keys and mousebuttons to actions
 */
public class Hello extends SimpleApplication {

    public static void main(String[] args) {
        Hello app = new Hello();
        app.start();

    }
    protected Node player;
    protected Bird bird;
    protected Spatial dessert;
    protected Event event;
    Boolean isRunning = true;

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("burung", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        flyCam.setMoveSpeed(50);
        dessert = assetManager.loadModel("Scenes/Dessert.j3o");
        dessert.setLocalScale(2f);

        rootNode.attachChild(dessert);

        Spatial burung = (Spatial) geom;
        burung = assetManager.loadModel("Models/Rham-Phorynchus/Rham-Phorynchus.j3o");
        burung.setLocalTranslation(0, 10, 0);
        bird = new Bird(burung);

        rootNode.attachChild(bird.getSpatial());
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        this.event = new Event(bird);
    }

    @Override
    public void simpleUpdate(float tpf) {

        bird.respawn();
    }

        @Override
        public void simpleRender(RenderManager rm) {

    }
}
