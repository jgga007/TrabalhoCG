package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.showSettings=false;
        app.start();
    }
    
    private Node cenario;
    private Node shootables;
    private Geometry mark;

    public Main() {
    }

    @Override
    public void simpleInitApp() {
        
        initCrossHairs();
        initKeys();
        initMark(); 
       
        cam.setLocation(new Vector3f(0,0.8f,10));
        //flyCam.setEnabled(false);//trava movimentação da camera com o mouse
                
        cenario = new Node("Cenario");
        shootables = new Node("Shootables");
        
        Box chao = new Box(1, 0.05f, 10);
        Geometry geomChao = new Geometry("Chao", chao);
        Material matChao = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matChao.setColor("Color", ColorRGBA.Blue);
        geomChao.setMaterial(matChao);
        matChao.setTexture("ColorMap", assetManager.loadTexture("Textures/Chao.jpg"));
        cenario.attachChild(geomChao);
        
        Box paredeEsq = new Box(0.05f, 0.6f, 10);
        Geometry geomParedeEsq = new Geometry("ParedeEsq", paredeEsq);
        Material matParede = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParede.setColor("Color", ColorRGBA.Red);
        geomParedeEsq.setMaterial(matParede);
        geomParedeEsq.setLocalTranslation(-1, 0.6f,0);
        matParede.setTexture("ColorMap", assetManager.loadTexture("Textures/Parede.jpg"));
        cenario.attachChild(geomParedeEsq);
        
        Box paredeDir = new Box(0.05f, 0.6f, 10);
        Geometry geomParedeDir = new Geometry("ParedeEsq", paredeDir);
        geomParedeDir.setMaterial(matParede);
        geomParedeDir.setLocalTranslation(1, 0.6f,0);
        cenario.attachChild(geomParedeDir);
        
        Box teto = new Box(1, 0.05f, 10);
        Geometry geomTeto = new Geometry("Teto", teto);
        Material matTeto = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matTeto.setColor("Color", ColorRGBA.Orange);
        geomTeto.setMaterial(matTeto);
        geomTeto.setLocalTranslation(0, 1.2f,0);
        matTeto.setTexture("ColorMap", assetManager.loadTexture("Textures/Parede.jpg"));
        cenario.attachChild(geomTeto);
       
        Box paredeFinal = new Box(1, 0.6f, 0.1f);
        Geometry geomParedeFinal = new Geometry("ParedeFinal", paredeFinal);
        Material matParedeFinal = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParedeFinal.setColor("Color", ColorRGBA.Magenta);
        geomParedeFinal.setMaterial(matParedeFinal);
        geomParedeFinal.setLocalTranslation(0,0.6f,-10);
        matParedeFinal.setTexture("ColorMap", assetManager.loadTexture("Textures/Parede.jpg"));
        cenario.attachChild(geomParedeFinal);
        
        Box teste = new Box(1, 06f, 1);
        Geometry geomteste = new Geometry("Teste", teste);
        Material matteste = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matteste.setColor("Color", ColorRGBA.Red);
        geomteste.setMaterial(matteste);
        geomteste.setLocalTranslation(0,06f,0);
        //matteste.setTexture("ColorMap", assetManager.loadTexture("Textures/Parede.jpg"));
        shootables.attachChild(geomteste);
        
    
        rootNode.attachChild(cenario);
        rootNode.attachChild(shootables);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    
    /** Declaring the "Shoot" action and mapping to its triggers. */
  private void initKeys() {
    inputManager.addMapping("Shoot",
      new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
      new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
    inputManager.addListener(actionListener, "Shoot");
    inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addListener(actionListener, "Walk");
    inputManager.addMapping("Side_A", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addListener(actionListener, "Side_A");
    inputManager.addMapping("Side_D", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addListener(actionListener, "Side_D");
    inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addListener(actionListener, "Back");
    inputManager.addMapping("Begin", new KeyTrigger(KeyInput.KEY_B));
    inputManager.addListener(actionListener, "Begin");
  }
  
  /** Defining the "Shoot" action: Determine what was hit and how to respond. */
  private ActionListener actionListener = new ActionListener() {
    
    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {     
         //if (name.equals("Begin") && !keyPressed) {
         //i = 0;
         //Texto.removeFromParent();
         //Texto2.removeFromParent();
         //cont = 0;
        //}
               
        if (name.equals("Shoot") && !keyPressed) {      
        // 1. Reset results list.
        CollisionResults results = new CollisionResults();
        // 2. Aim the ray from cam loc to cam direction.
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        // 3. Collect intersections between Ray and Shootables in results list.
        // DO NOT check collision with the root node, or else ALL collisions will hit the
        // skybox! Always make a separate node for objects you want to collide with.
        shootables.collideWith(ray, results);
        // 4. Print the results
        System.out.println("----- Collisions? " + results.size() + "-----");
        for (int i = 0; i < results.size(); i++) {
          // For each hit, we know distance, impact point, name of geometry.
          float dist = results.getCollision(i).getDistance();
          Vector3f pt = results.getCollision(i).getContactPoint();
          String hit = results.getCollision(i).getGeometry().getName();
          System.out.println("* Collision #" + i);
          System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
        }
        // 5. Use the results (we mark the hit object)
        if (results.size() > 0) {
          // The closest collision point is what was truly hit:
          CollisionResult closest = results.getClosestCollision();
          // Let's interact - we mark the hit with a red dot.
          mark.setLocalTranslation(closest.getContactPoint());
          rootNode.attachChild(mark);
          closest.getGeometry().removeFromParent();
          //cont++;
        } else {
          // No hits? Then remove the red mark.
          rootNode.detachChild(mark);
        }
      }
    }
  };
    
    /** A red ball that marks the last spot that was "hit" by the "shot". */
  protected void initMark() {
    Sphere sphere = new Sphere(5, 5, 0.1f);
    mark = new Geometry("BOOM!", sphere);
    Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mark_mat.setColor("Color", ColorRGBA.Red);
    mark.setMaterial(mark_mat);
  }
    
    
    /** A centred plus sign to help the player aim. */
  protected void initCrossHairs() {
    setDisplayStatView(false);
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+"); // crosshairs
    ch.setColor(ColorRGBA.Green);
    ch.setLocalTranslation( // center
      settings.getWidth() / 2 - ch.getLineWidth()/2,
      settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
    guiNode.attachChild(ch);
  }  
  
}
