package mygame;

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
import java.util.Random;

/**
 * Sample 5 - how to map keys and mousebuttons to actions
 */
public class HelloInput2 extends SimpleApplication {

    Random rand;
    private int i=0;
    
    
    public static void main(String[] args) {
        HelloInput2 app = new HelloInput2();
        app.start();
        
    }
    protected Node player;
    protected Bird bird;
    protected Spatial dessert;
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
    }

    @Override
    public void simpleUpdate(float tpf) {
        rand = new Random();
        float temp = rand.nextInt(11);
        //bird.setLocation(0,temp,0);
//        Vector3f posisi = bird.getLocation();
//        float x=posisi.x;
//        float y=posisi.y;
//        float z=posisi.z;
//        System.out.println("y:"+y);
//        System.out.println("x"+x);
//        System.out.println("z"+z);
        Vector3f ff = new Vector3f(-40,10,0);
        
        Vector3f tempatBaru = new Vector3f(0,temp,0);
        
        
        if(bird.getLocation().equals(ff)){
            bird.moveForward(1);
            
            
        }
        else{
            bird.setLocation(tempatBaru);
            
            
            
        }
        
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
}
