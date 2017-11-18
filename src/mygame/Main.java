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

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {
private Cactus kotak;
private EventManager eventer;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        nifty.addScreen("start", new ScreenBuilder("start") {
            {
                controller(new DefaultScreenController());
                layer(new LayerBuilder("background") {
                    {
                        childLayoutCenter();
                        backgroundColor("#000f");
                        // <!-- ... -->
                    }
                });

                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutVertical();
                        backgroundImage("Interface/trex.jpg");
                        backgroundColor("#0000");

                        // panel added
                        panel(new PanelBuilder("panel_top") {
                            {
                                childLayoutCenter();
                                alignCenter();
//                                backgroundColor("#f008");
                                height("25%");
                                width("75%");
                            }
                        });

                        panel(new PanelBuilder("panel_mid") {
                            {
                                childLayoutCenter();
                                alignCenter();
//                                backgroundColor("#0f08");
                                height("50%");
                                width("75%");
                            }
                        });

                        panel(new PanelBuilder("panel_bottom") {
                            {
                                childLayoutHorizontal();
                                alignCenter();
//                                backgroundColor("#00f8");
                                height("25%");
                                width("75%");

                                panel(new PanelBuilder("panel_bottom_left") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
//                                        backgroundColor("#44f8");
                                        height("50%");
                                        width("50%");

                                        // add control
                                        control(new ButtonBuilder("StartButton", "Play") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                visibleToMouse(true);
                                                interactOnClick("gotoScreen(hud)");
                                            }
                                        });

                                    }
                                });

                                panel(new PanelBuilder("panel_bottom_right") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
//                                        backgroundColor("#88f8");
                                        height("50%");
                                        width("50%");

                                        // add control
                                        control(new ButtonBuilder("QuitButton", "Quit") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                visibleToMouse(true);
                                                interactOnClick("quitGame");
                                            }
                                        });

                                    }
                                });
                            }
                        }); // panel added
                    }
                });

            }
        }.build(nifty));

        nifty.addScreen("hud", new ScreenBuilder("hud") {
            {
                controller(new DefaultScreenController());

                layer(new LayerBuilder("background") {
                    {
                        childLayoutCenter();
//                        backgroundColor("#000f");
                        // <!-- ... -->
                    }
                });

                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutHorizontal();
//                        backgroundColor("#0000");

                        // panel added
                        panel(new PanelBuilder("panel_left") {
                            {
                                childLayoutVertical();
//                                backgroundColor("#0f08");
                                height("100%");
                                width("80%");
                                // <!-- spacer -->
                            }
                        });

                        panel(new PanelBuilder("panel_right") {
                            {
                                childLayoutVertical();
//                                backgroundColor("#00f8");
                                height("100%");
                                width("20%");

                                panel(new PanelBuilder("panel_top_right1") {
                                    {
                                        childLayoutCenter();
//                                        backgroundColor("#00f8");
                                        height("15%");
                                        width("100%");
                                    }
                                });

                                panel(new PanelBuilder("panel_top_right2") {
                                    {
                                        childLayoutCenter();
//                                        backgroundColor("#44f8");
                                        height("15%");
                                        width("100%");
                                    }
                                });

                                panel(new PanelBuilder("panel_bot_right") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
//                                        backgroundColor("#88f8");
                                        height("70%");
                                        width("100%");
                                    }
                                });
                            }
                        }); // panel added
                    }
                });
            }
        }.build(nifty));

        nifty.gotoScreen("start");
        
        
        
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
        
        eventer = new EventManager(kotak);
        //kotak.moveForward(100);
        //kotak.moveForward(100);
        //kotak.moveForward(100);
        //kotak.moveForward(100);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //kotak.respawn(0.9f);
        eventer.doSomething();
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code 
    }
}
