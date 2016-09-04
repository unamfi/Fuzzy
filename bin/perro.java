

 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class perro extends JPanel
                        implements ActionListener,
                                   WindowListener,
                                   ChangeListener {
    //Parametros de la animacion
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 30;
    static final int FPS_INIT = 15;    //Frames por segundo
    int frameNumber = 0;
    int NUM_FRAMES = 14;
    ImageIcon[] images = new ImageIcon[NUM_FRAMES];
    int delay;
    int intentos=1;
    int bandera;
    int inicial;
    Timer timer;
    boolean frozen = false;
    long tiempo1;
    long tiempo2;
    int tiempo;
 
    
    long time_start, time_end;
    boolean exito;
 
    //label tipo ImageIcon para mostrar al perrito.
    JLabel picture;
    JLabel model;
    
    public void msgbox(String s){
        JOptionPane.showMessageDialog(null, s);
    }
    
    public perro() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
 
        delay = 1000 / FPS_INIT;
 
        //creacion del label.
        JLabel sliderLabel = new JLabel("¡¿Que tan rapido?! :D", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        //creacion del slider.
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                                              FPS_MIN, FPS_MAX, FPS_INIT);
         
 
        framesPerSecond.addChangeListener(this);
 
        //Turn on labels at major tick marks.
 
        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        framesPerSecond.setFont(font);
 
        //Create the label that displays the animation.
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));
        updatePicture(0); //display first frame
 
        //Put everything together.
        add(sliderLabel);
        add(framesPerSecond);
        add(picture);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
 
        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay * 7); //We pause animation twice per cycle
                                          //by restarting the timer
        timer.setCoalesce(true);
    }
   
    void addWindowListener(Window w) {
        w.addWindowListener(this);
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
            delay = 250/ fps;                
            timer.setDelay(delay);
            timer.setInitialDelay(delay * 10);
            if (frozen) startAnimation();
            
            if (fps == 0) {
                if (!frozen) stopAnimation();
            } else {
                if(bandera == 0){                    
                    if( fps>= 0 && fps <= 5){                        
                        msgbox("Ahora MUY rapido");
                        bandera = 1;                    
                    }   
                    else {
                        msgbox("Es MUY rapido aun D:");
                        bandera = 0;
                        intentos++;
                
                    }
                }
                else if (bandera == 1){                    
                    if (fps >=25 && fps <=30){
                        msgbox("Ahora NO TAN rapido PERO NO TAN lentento");                        
                        bandera = 2;
                    }
                    else {
                        msgbox("Es MUY lento aun D:");
                        bandera=1;
                        intentos++;
                    }                    
                }
                else if(bandera == 2){
                    if(fps>=13 && fps <=17 ){
                        msgbox("¡Bien hecho! :D");                        
                        bandera =3;                
                    }
                    else if(fps>17){
                        msgbox("Es MUY rapido aun D:");                        
                        intentos++;                    
                    }
                    else if (fps<13){
                        msgbox("Es MUY lento D:");                        
                        intentos++;
                    }
                } 
                if(bandera == 3){
                    tiempo2 = java.util.Calendar.getInstance().getTimeInMillis();
                    tiempo = (int) ((tiempo2-tiempo1)/1000);
                    System.out.println("tiempo: " + tiempo +"  intentos: " + intentos);
                    
                    this.stopAnimation();
                
                }
                    
            }
        }
    }
 
    public void startAnimation() {        
        timer.start();
        frozen = false;
    }
 
    public void stopAnimation() {        
        timer.stop();
        this.frozen = true;
        System.out.println("frozen =" + this.frozen);
        
    }
 
  
    public void actionPerformed(ActionEvent e) {
        
        if (inicial ==0){
                msgbox("¡Mueve la barra para hacer que el perro se mueva! Primero MUY lento");
                tiempo1=java.util.Calendar.getInstance().getTimeInMillis();
                inicial ++;
        }
        //avance de las imagenes
        
        if (frameNumber == (NUM_FRAMES - 1)) {
            frameNumber = 0;
        } else {
            frameNumber++;
        }
 
        updatePicture(frameNumber); //Muestra la figura siguiente
 
        if ( frameNumber==(NUM_FRAMES - 1)
          || frameNumber==(NUM_FRAMES/2 - 1) ) {
            timer.restart();
        }
    }
 
    protected void updatePicture(int frameNum) {
        //Toma la imagen si aun no la tenemos lista.
        if (images[frameNumber] == null) {
            images[frameNumber] = createImageIcon("images/doggy/T"
                                                  + frameNumber
                                                  + ".gif");
        }
 
        //Pone la imagen
        if (images[frameNumber] != null) {
            picture.setIcon(images[frameNumber]);
        } else { //Imagen no encontrada
            picture.setText("image #" + frameNumber + " not found");
        }
    }
 
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = perro.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
  
    public float[] createAndShowGUI() {
        //declaracion de ventana
        JFrame frame = new JFrame("Nivel 3 ¿Qué tan rapido?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //perro animator = new perro();
        
                 
        //contenido de la ventana.
        frame.add(this, BorderLayout.CENTER);
        
 
        //mostar la ventana
        frame.pack();
        frame.setVisible(true);
        this.startAnimation(); 
        
        
        while(true) {
            System.out.println("while... true..");
	        if(this.frozen)
	        {
		        float[] vector = new float[2];
				vector[0] = intentos;
				vector[1] = tiempo;
				frame.dispose();
				return vector; 
	        }
	        
        
        }

        
    }
 
}