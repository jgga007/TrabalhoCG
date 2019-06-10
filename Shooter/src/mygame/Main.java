package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
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
 *
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
    private Node shootables;
    private Geometry mark;
    private Node portaNode;
    private AudioNode audio_gun;
    private AudioNode audio_nature;

    public Main() {
    }
   
    

    @Override
    public void simpleInitApp() {

        initCrossHairs();
        initKeys();
        initMark();
        initAudio();

        cam.setLocation(new Vector3f(0, 0.8f, 100));
        //flyCam.setEnabled(false);//trava movimentação da camera com o mouse
        obstaculos = new Node("obstaculos");
        cenario = new Node("Cenario");
        deaphObst = new Node("deaphObst");
        shootables = new Node("Shootables");
        portaNode = new Node("Portinhas");

        Box paredeFinal = new Box(2, 1.2f, 0.1f);
        Geometry geomParedeFinal = new Geometry("ParedeFinal", paredeFinal);
        Material matParedeFinal = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParedeFinal.setColor("Color", ColorRGBA.Magenta);
        geomParedeFinal.setMaterial(matParedeFinal);
        geomParedeFinal.setLocalTranslation(0, 1.2f, -100);
        matParedeFinal.setTexture("ColorMap", assetManager.loadTexture("Textures/parede.jpg"));
        cenario.attachChild(geomParedeFinal);

        Box teste = new Box(1, 06f, 1);
        Geometry geomteste = new Geometry("Teste", teste);
        Material matteste = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matteste.setColor("Color", ColorRGBA.Red);
        geomteste.setMaterial(matteste);
        geomteste.setLocalTranslation(0, 06f, 0);
        matteste.setTexture("ColorMap", assetManager.loadTexture("Textures/parede.jpg"));
        shootables.attachChild(geomteste);

        rootNode.attachChild(cenario);
        criaObstaculo();
        rootNode.attachChild(obstaculos);

    }

    public void criaObstaculo() {

        Box obst = new Box(0.4f, 0.4f, 0.4f);
        Geometry geomObst;
        Material caixa = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        caixa.setTexture("ColorMap", assetManager.loadTexture("Textures/box.jpg"));

        //material da lava
        Box dObst = new Box(0.4f, 0.01f, 0.4f);
        Geometry geomDObst;
        Material lava = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        lava.setTexture("ColorMap", assetManager.loadTexture("Textures/lava.jpg"));

        //caixa parede esquerda inferior
        for (int i = 0; i < 100; i += 15) {
            geomObst = new Geometry("obst" + i, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(-1.45f, 0.45f, i);
            obstaculos.attachChild(geomObst);
        }

        //sobre a caixa inf da parede esq
        for (int i = 0; i < 100; i += 30) {
            geomObst = new Geometry("obst" + i + 1, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(-1.45f, 1.2f, i);
            obstaculos.attachChild(geomObst);
        }

        //lava lado esquerdo 
        for (int i = 0; i < 100; i += 15) {
            geomDObst = new Geometry("Dobst" + i, dObst);
            geomDObst.setMaterial(lava);
            geomDObst.setLocalTranslation(-0.6f, 0.1f, i);
            obstaculos.attachChild(geomDObst);
            
            
            geomDObst = new Geometry("Dobst" + i, dObst);
            geomDObst.setMaterial(lava);
            geomDObst.setLocalTranslation(0.6f, 0.1f, i+10);
            obstaculos.attachChild(geomDObst);
            
             
            
        }
        //caixa parede esq inferior
        for (int i = 10; i < 100; i += 15) {
            geomObst = new Geometry("obst" + i, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(-0.6f, 0.45f, i);
            obstaculos.attachChild(geomObst);
        }

        //caixa parede dir inferior
        for (int i = 10; i < 100; i += 15) {
            geomObst = new Geometry("obst" + i, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(1.5f, 0.45f, i);
            obstaculos.attachChild(geomObst);
            
            geomObst = new Geometry("obst" + i, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(1f, 0.45f, i+5);
            obstaculos.attachChild(geomObst);
            
            geomObst = new Geometry("obst" + i+1, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(1f, 1.2f, i+5);
            obstaculos.attachChild(geomObst);
            
            geomObst = new Geometry("obst" + i+2, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(0.7f, 0.45f, i+10);
            obstaculos.attachChild(geomObst);
            
            geomObst = new Geometry("obst" + i+3, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(0.7f, 1.2f, i+10);
            obstaculos.attachChild(geomObst);
            
            
            
            
        }
        //sobre a caixa inf da parede dir
        for (int i = 10; i < 100; i += 30) {
            geomObst = new Geometry("obst" + i + 1, obst);
            geomObst.setMaterial(caixa);
            geomObst.setLocalTranslation(1.5f, 1.2f, i);
            obstaculos.attachChild(geomObst);
        }
        criaCenario();
        criaPorta(93);

    }

    public void criaCenario() {

        Box chao = new Box(2, 0.05f, 10);
        Geometry geomChao;
        Material matChao = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matChao.setTexture("ColorMap", assetManager.loadTexture("Textures/Chao.jpg"));

        Box parede = new Box(0.05f, 1.2f, 10);
        Geometry geomParedeEsq, geomParedeDir;
        Material matParede = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matParede.setTexture("ColorMap", assetManager.loadTexture("Textures/parede.jpg"));

        Box teto = new Box(2, 0.05f, 10);
        Geometry geomTeto;
        Material matTeto = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matTeto.setTexture("ColorMap", assetManager.loadTexture("Textures/teto.jpg"));


        for (int i = 0; i < 120; i += 20) {

            geomParedeEsq = new Geometry("paredeEsq" + i, parede);
            geomParedeEsq.setMaterial(matParede);
            geomParedeEsq.setLocalTranslation(-2, 1.2f, i);
            cenario.attachChild(geomParedeEsq);

            geomParedeDir = new Geometry("paredeDir" + i, parede);
            geomParedeDir.setMaterial(matParede);
            geomParedeDir.setLocalTranslation(2, 1.2f, i);
            cenario.attachChild(geomParedeDir);

            geomChao = new Geometry("paredeEsq" + i, chao);
            geomChao.setMaterial(matChao);
            geomChao.setLocalTranslation(0, 0, i);
            cenario.attachChild(geomChao);

            geomTeto = new Geometry("teto" + i, teto);
            geomTeto.setMaterial(matTeto);
            geomTeto.setLocalTranslation(0, 2.2f, i);
            cenario.attachChild(geomTeto);

        }

    }

    public void criaPorta(float posZ) {

        Box Porta = new Box(1f, 1f, 0.1f);
        Geometry geomPortaDir1, geomPortaEsq1, geomPortaDir2, geomPortaEsq2;
        Material porta = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        porta.setTexture("ColorMap", assetManager.loadTexture("Textures/porta.jpg"));

        geomPortaDir1 = new Geometry("Porta", Porta);
        geomPortaDir1.setMaterial(porta);
        geomPortaDir1.setLocalTranslation(1f, 1f, posZ);
        portaNode.attachChild(geomPortaDir1);

        geomPortaEsq1 = new Geometry("Porta2", Porta);
        geomPortaEsq1.setMaterial(porta);
        geomPortaEsq1.setLocalTranslation(-1f, 1, posZ);
        portaNode.attachChild(geomPortaEsq1);
        
        geomPortaDir2 = new Geometry("Porta3", Porta);
        geomPortaDir2.setMaterial(porta);
        geomPortaDir2.setLocalTranslation(1f, 1, posZ-20);
        portaNode.attachChild(geomPortaDir2);

        geomPortaEsq2 = new Geometry("Porta4", Porta);
        geomPortaEsq2.setMaterial(porta);
        geomPortaEsq2.setLocalTranslation(-1f, 1, posZ-20);
        portaNode.attachChild(geomPortaEsq2);
        rootNode.attachChild(portaNode);
        

    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code

        //abre a porta com a posição da camera
        if (cam.getLocation().z >= 95 && cam.getLocation().z <= 96) {
            for (float i = 1; i < 2; i += 0.01) {
                portaNode.getChild("Porta").setLocalTranslation((float) i, 1, 93);
                portaNode.getChild("Porta2").setLocalTranslation((float) i * -1, 1, 93);
            }
        }
        
        if (cam.getLocation().z >= 75 && cam.getLocation().z <= 76) {
            for (float i = 1; i < 2; i += 0.01) {
                portaNode.getChild("Porta3").setLocalTranslation((float) i, 1, 73);
                portaNode.getChild("Porta4").setLocalTranslation((float) i * -1, 1, 73);
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Declaring the "Shoot" action and mapping to its triggers.
     */
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

    /**
     * Defining the "Shoot" action: Determine what was hit and how to respond.
     */
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
                audio_gun.playInstance();
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

    /**
     * A red ball that marks the last spot that was "hit" by the "shot".
     */
    protected void initMark() {
        Sphere sphere = new Sphere(5, 5, 0.1f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }

    /**
     * A centred plus sign to help the player aim.
     */
    protected void initCrossHairs() {
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setColor(ColorRGBA.Green);
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }
    
    private void initAudio() {
    /* gun shot sound is to be triggered by a mouse click. */
    audio_gun = new AudioNode(assetManager, "Sounds/Gun.wav", DataType.Buffer);
    audio_gun.setPositional(false);
    audio_gun.setLooping(false);
    audio_gun.setVolume(2);
    rootNode.attachChild(audio_gun);
    
    /* nature sound - keeps playing in a loop.*/
    audio_nature = new AudioNode(assetManager, "Sounds/Background2.ogg", DataType.Stream);
    audio_nature.setLooping(true);  // activate continuous playing
    audio_nature.setPositional(true);
    audio_nature.setVolume(3);
    rootNode.attachChild(audio_nature);
    audio_nature.play(); // play continuously!
  }

}
