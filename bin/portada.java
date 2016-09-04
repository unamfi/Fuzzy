
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
 
/*
 * nivel1.java requires all the files in the images/doggy
 * directory.
 */
public class portada extends JPanel
                        implements ActionListener,
                                   WindowListener,
                                   ChangeListener {
    //Set up animation parameters.
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 30;
    static final int FPS_INIT = 15;    //initial frames per second
    int frameNumber = 0;
    int NUM_FRAMES=4;
    ImageIcon[] images = new ImageIcon[NUM_FRAMES];
    int delay;
    Timer timer;
    boolean frozen = true;
    long time_start, time_end;
    boolean exito;
    
 
    //This label uses ImageIcon to show the doggy pictures.
    JLabel picture;
    JLabel model;
 
    public portada() {
        //NUM_FRAMES=numEstrellas;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        delay = 4000 / FPS_INIT;
 
        
 
       
        //Create the label that displays the animation.
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));
        updatePicture(0); //display first frame
 
        //Put everything together
        add(picture);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
 
        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay * 7); //We pause animation twice per cycle
                                          //by restarting the timer
        timer.setCoalesce(true);
    }
   

    public void windowIconified(WindowEvent e) {
        stopAnimation();
    }
    public void windowDeiconified(WindowEvent e) {
        startAnimation();
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
 
    // Control del slider. 
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int)source.getValue();
            if (fps == 0) {
                if (!frozen) stopAnimation();
            } else {
                delay = 250/ fps;
                timer.setDelay(delay);
                timer.setInitialDelay(delay * 10);
                if (frozen) startAnimation();
            }
        }
    }
 
   
    public void startAnimation() {        
        timer.start();
        frozen = false;
    }
 
    public void stopAnimation() {
        //Stop the animating thread.
        timer.stop();
        frozen = true;
    }
 
    //Control de velocidad.
    public void actionPerformed(ActionEvent e/*int NUM_FRAMES*/) {
        //avance de las imagenes
       // NUM_FRAMES=4;
        
        
   //    /* 
        if (frameNumber < (NUM_FRAMES -(4))) {
            //frameNumber = 0;          
           frameNumber++;
        }
// */
        updatePicture(frameNumber); //display the next picture
 
        
            
        
    }
 
    //Cambio de imagen
    protected void updatePicture(int frameNumber) {
        //Get the image if we haven't already.
        if (images[frameNumber] == null) {
            images[frameNumber] = createImageIcon("images/portada/portada.jpg");
        }
 
        //Set the image.
        if (images[frameNumber] != null) {
            picture.setIcon(images[frameNumber]);
        } else { //image not found
            picture.setText("image #" + frameNumber + " not found");
        }
    }
 
    //Cargador de imagenes
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = estrellas.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
   
  
    public static void muestraPortada() {
        
        //declaracion de ventana
        JFrame frame = new JFrame("Inicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        portada animator = new portada();
        
                 
        //contenido de la ventana.
        frame.add(animator, BorderLayout.CENTER);
        
 
        //mostar la ventana
        frame.pack();
        frame.setVisible(true);
        animator.startAnimation(); 
        
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        animator.stopAnimation(); 
        
        frame.dispose();
        
        return;
        
        
    }
}