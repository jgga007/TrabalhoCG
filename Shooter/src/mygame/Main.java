package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
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

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        //app.showSettings=false;
        app.start();
    }
    
    private Node cenario;
    private Node obstaculos;
    private Node deaphObst;

    public Main() {
    }

    @Override
    public void simpleInitApp() {
        
        initCrossHairs();
        //initKeys();
        //initMark(); 
       
        cam.setLocation(new Vector3f(0,0.8f,100));
        //flyCam.setEnabled(false);//trava movimentação da camera com o mouse
        obstaculos = new Node("obstaculos");
        cenario = new Node("Cenario");
        deaphObst = new Node("deaphObst");
        Box chao = new Box(2, 0.05f, 100);
        Geometry geomChao = new Geometry("Chao", chao);
        Material matChao = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //matChao.setColor("Color", ColorRGBA.Blue);
        geomChao.setMaterial(matChao);
        matChao.setTexture("ColorMap", assetManager.loadTexture("Textures/Chao.jpg"));
        cenario.attachChild(geomChao);
        
        Box paredeEsq = new Box(0.05f, 1.2f, 100);
        Geometry geomParedeEsq = new Geometry("ParedeEsq", paredeEsq);
        Material matParede = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParede.setColor("Color", ColorRGBA.Red);
        geomParedeEsq.setMaterial(matParede);
        geomParedeEsq.setLocalTranslation(-2, 1.2f,0);
        matParede.setTexture("ColorMap", assetManager.loadTexture("Textures/Parede.jpg"));
        cenario.attachChild(geomParedeEsq);
        
        Box paredeDir = new Box(0.05f, 1.2f, 100);
        Geometry geomParedeDir = new Geometry("ParedeEsq", paredeDir);
        geomParedeDir.setMaterial(matParede);
        geomParedeDir.setLocalTranslation(2, 1.2f,0);
        cenario.attachChild(geomParedeDir);
        
        Box teto = new Box(2, 0.05f, 100);
        Geometry geomTeto = new Geometry("Teto", teto);
        Material matTeto = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matTeto.setColor("Color", ColorRGBA.Orange);
        geomTeto.setMaterial(matTeto);
        geomTeto.setLocalTranslation(0,2.2f,0);
        matTeto.setTexture("ColorMap", assetManager.loadTexture("Textures/Parede.jpg"));
        cenario.attachChild(geomTeto);
       
        Box paredeFinal = new Box(2, 1.2f, 0.1f);
        Geometry geomParedeFinal = new Geometry("ParedeFinal", paredeFinal);
        Material matParedeFinal = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParedeFinal.setColor("Color", ColorRGBA.Magenta);
        geomParedeFinal.setMaterial(matParedeFinal);
        geomParedeFinal.setLocalTranslation(0,1.2f,-100);
        matParedeFinal.setTexture("ColorMap", assetManager.loadTexture("Textures/Parede.jpg"));
        cenario.attachChild(geomParedeFinal);
        rootNode.attachChild(cenario);
        criaObstaculo();
        rootNode.attachChild(obstaculos);
                
    }

    public void criaObstaculo(){
        
        Box obst = new Box(0.4f, 0.4f, 0.4f);
        Geometry geomObst;
        Material caixa = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        caixa.setTexture("ColorMap", assetManager.loadTexture("Textures/box.jpg"));
        
        //material da lava
        Box dObst = new Box(0.4f, 0.01f, 0.4f);
        Geometry geomDObst;
        Material lava = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        lava.setTexture("ColorMap", assetManager.loadTexture("Textures/lava.jpg"));
        
            
        //parede esquerda inferior
        for(int i=0;i<100;i+=15){
            geomObst = new Geometry("obst"+i, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(-1.45f,0.45f,i);
            obstaculos.attachChild(geomObst);
        }
        
        //sobre a caixa inf da parede esq
        for(int i=0;i<100;i+=30){
            geomObst = new Geometry("obst"+i+1, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(-1.45f,1.2f,i);
            obstaculos.attachChild(geomObst);
        }
        
        //lava lado esquerdo 
        for(int i=0;i<100;i+=15){
            geomDObst = new Geometry("Dobst"+i, dObst);
            geomDObst.setMaterial(lava);
            geomDObst.setLocalTranslation(-0.5f,0.1f,i);
            obstaculos.attachChild(geomDObst);
        }
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
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