package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
        app.start();
    }
    
    private Node cenario;

    public Main() {
    }

    @Override
    public void simpleInitApp() {
       
        cam.setLocation(new Vector3f(0,0.8f,10));
        //flyCam.setEnabled(false);//trava movimentação da camera com o mouse
        
        cenario = new Node("Cenario");
        Box chao = new Box(1, 0.05f, 10);
        Geometry geomChao = new Geometry("Chao", chao);
        Material matChao = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matChao.setColor("Color", ColorRGBA.Blue);
        geomChao.setMaterial(matChao);
        cenario.attachChild(geomChao);
        
        Box paredeEsq = new Box(0.05f, 0.6f, 10);
        Geometry geomParedeEsq = new Geometry("ParedeEsq", paredeEsq);
        Material matParede = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParede.setColor("Color", ColorRGBA.Red);
        geomParedeEsq.setMaterial(matParede);
        geomParedeEsq.setLocalTranslation(-1, 0.6f,0);
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
        cenario.attachChild(geomTeto);
        
        int i =0;
        
        Box paredeFinal = new Box(1, 0.6f, 0.1f);
        Geometry geomParedeFinal = new Geometry("ParedeFinal", paredeFinal);
        Material matParedeFinal = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParedeFinal.setColor("Color", ColorRGBA.Magenta);
        geomParedeFinal.setMaterial(matParedeFinal);
        geomParedeFinal.setLocalTranslation(0,0.6f,-10);
        cenario.attachChild(geomParedeFinal);

        
        
        rootNode.attachChild(cenario);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
