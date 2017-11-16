package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.control.GhostControl;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    private int score;
    BitmapText scoreText, highScore;
    float temp;

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        scoreText = new BitmapText(guiFont, false);
        scoreText.setText("Score: 0");
        guiNode.attachChild(scoreText);
        highScore = new BitmapText(guiFont, false);
        highScore.setText("High Score: 0");
        guiNode.attachChild(highScore);
        scoreText.setLocalTranslation((cam.getWidth() - scoreText.getLineWidth()) / 2.0f,
                scoreText.getLineHeight(), 0.0f);
        highScore.setLocalTranslation((cam.getWidth() + highScore.getLineWidth() + highScore.getLineWidth()) / 2.0f,
                highScore.getLineHeight(), 1.0f);

    }

    @Override
    public void simpleUpdate(float tpf) {
        if (temp >= 1) {
            score++;
            scoreText.setText("Score: " + score);
            scoreText.setLocalTranslation((cam.getWidth() - scoreText.getLineWidth()) / 2.0f,
                    scoreText.getLineHeight(), 0.0f);
            highScore.setText("High Score: " + score);
            temp = 0;
        }
        else{
            temp+=tpf;
        }
        guiNode.attachChild(scoreText);
    }

//===================================           
//        scoreText.setText("Score: 0");
//        guiNode.attachChild(scoreText);
//
//        scoreText.setLocalTranslation((cam.getWidth() - scoreText.getLineWidth()) / 2.0f,
//                scoreText.getLineHeight(), 0.0f);
//    }
//
//    @Override
//    public void simpleUpdate(float tpf) {
//        scoreText.setText("Score: " + score);
//        scoreText.setLocalTranslation((cam.getWidth() - scoreText.getLineWidth()) / 2.0f,
//                scoreText.getLineHeight(), 0.0f);
//    }
//    public void collision(PhysicsCollisionEvent event) {
//        Spatial nodeA = event.getNodeA();
//        Spatial nodeB = event.getNodeB();
//        
//        String nameA = nodeA == null ? "" : nodeA.getName();
//        String nameB = nodeB == null ? "" : nodeB.getName();
//        
//        if(nameA.equals("player") && nameB.startsWith("pickUp")) {
//            GhostControl pickUpControl = nodeB.getControl(GhostControl.class);
//            if(pickUpControl != null && pickUpControl.isEnabled()) {
//                pickUpControl.setEnabled(false);
//                nodeB.removeFromParent();
//                nodeB.setLocalScale(0.0f);
//                score += 1;
//            }
//        } else if(nameA.startsWith("pickUp") && nameB.equals("player")) {
//            GhostControl pickUpControl = nodeA.getControl(GhostControl.class);
//            if(pickUpControl != null && pickUpControl.isEnabled()) {
//                pickUpControl.setEnabled(false);
//                nodeA.setLocalScale(0.0f);
//                score += 1;
//            }
//        }
//    }
//    @Override
//    public void simpleRender(RenderManager rm) {
//        //TODO: add render code
//    }
}
