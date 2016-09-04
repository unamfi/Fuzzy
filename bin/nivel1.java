
//package nivel1;
import java.util.Calendar; 

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class nivel1 extends JPanel
                        implements ActionListener,
                                   WindowListener,
                                   ChangeListener {
    //Set up animation parameters.
	private JButton boton1,boton2,boton3;
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 30;
    static final int FPS_INIT = 15;    //initial frames per second
    int frameNumber = (int)(Math.random()*7);
    int NUM_FRAMES = 7;
    ImageIcon[] images = new ImageIcon[NUM_FRAMES];
    int delay;
    Timer timer;
    boolean frozen = false;
    long time_start, time_end;
    boolean exito;
    boolean inicio=true;
    static boolean finalizar=false;
    static float[] resultados = new float[2];
    //booleanos para las flechas**********************************************
  //  private boolean arriba, abajo, izquierda, derecha;
    static int numIntentos=1;
    long t_inicial,t_final;
	static long t_total=0;
 
    //This label uses ImageIcon to show the doggy pictures.
    JLabel picture;
    JLabel model;
 
    public nivel1() {
        
        EjecutaComando.EjecutaComando("open /Users/JulioCesar/Desktop/Fuzzy/bin/TutoElefante.swf");
        
        try{
            Thread.sleep(10000);
        }
        catch(InterruptedException e)
        {
            
        }
    
    
       	boton1=new JButton("Calentar");
        //boton1.setBounds(1,0,6,2);
        add(boton1);
        boton1.addActionListener(this);
        
        boton2=new JButton("Enfriar");
     //   boton2.setBounds(1,5,9,3);
        add(boton2);
        boton2.addActionListener(this);
        
        boton3=new JButton("Finalizar");
       // boton3.setBounds(1,10,9,3);
        add(boton3);
        boton3.addActionListener(this);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
 
        delay = 1000 / FPS_INIT;
        /*para manejar evento de las teclas******************************
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                actualiza(e.getKeyCode(), true);
            }

            public void keyReleased(KeyEvent e) {
                actualiza(e.getKeyCode(), false);
            }

            private void actualiza(int keyCode, boolean pressed) {
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        arriba = pressed;
                        break;

                    case KeyEvent.VK_DOWN:
                        abajo = pressed;
                        break;

                    case KeyEvent.VK_LEFT:
                        izquierda = pressed;
                        break;

                    case KeyEvent.VK_RIGHT:
                        derecha = pressed;
                        break;
                                     	}
            	}
        	});
        setFocusable(true);
    ***************************************/
 

 
        //Create the label that displays the animation.
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));
        updatePicture(0); //display first frame
 
        //Put everything together.

        add(picture);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
 
        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay * 7); //We pause animation twice per cycle
                                          //by restarting the timer
        timer.setCoalesce(true);
      //******************************boton
   
        
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
    public void actionPerformed(ActionEvent e) {
        //avance de las imagenes
     /*   if (frameNumber == (NUM_FRAMES - 1)) {
            frameNumber = 0;
        } else {
            frameNumber--;
        }*/
    	
    	//botones
    	if (e.getSource()==boton1) {
    		frameNumber++;
    		if(frameNumber>=6)
    			frameNumber=6;
    		updatePicture(frameNumber);
        }
    	if (e.getSource()==boton2) {
        	frameNumber--;
    		if(frameNumber<=0)
    			frameNumber=0;
    		updatePicture(frameNumber);
         }
    	if (e.getSource()==boton3) {
    		//finalizar tiempo
    		t_final=System.currentTimeMillis();
			System.out.println("tiempo final= "+t_final);
			t_total+=(t_final- t_inicial)/1000;
			System.out.println("tiempo total= "+t_total);
          
    		if(frameNumber!=3)
            {
    			
    			inicio=true;
    		}
           
    		else
            {
                finalizar=true;
                System.out.println("finalizar" + finalizar);
                //resultados[0]=numIntentos;
                //resultados[1]=t_total;
    			
    		}
    		numIntentos++;
    		System.out.println("numero de intentos= "+numIntentos);
        }
    	if(inicio)
    		updatePicture(frameNumber);
  /*  	
    	if(arriba){
    		frameNumber++;
    		if(frameNumber>=6)
    			frameNumber=6;
    		updatePicture(frameNumber);
    	}
    	if (abajo){
    		frameNumber--;
    		if(frameNumber<=0)
    			frameNumber=0;
    		updatePicture(frameNumber);
    	}
    */	
    	
 
     //   updatePicture(frameNumber); //display the next picture
 
        if ( frameNumber==(NUM_FRAMES - 1)
          || frameNumber==(NUM_FRAMES/2 - 1) ) {
            timer.restart();
        }
    }
 
    //Cambio de imagen
    protected void updatePicture(int frameNum) {
        //Get the image if we haven't already.
    	 System.out.println("inicial= "+frameNumber);
    	 
    	 
        if (images[frameNumber] == null) {
            images[frameNumber] = createImageIcon("images/elefante/elefante"
                                                  + frameNumber
                                                  + ".jpg");
        }
        if (inicio){
        	//iniciar tiempo
        	t_inicial=System.currentTimeMillis();
        	System.out.println("tiempo inicial= "+t_inicial);
        	inicio=false;
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
        java.net.URL imgURL = nivel1.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
  
    public float[] iniciarNivel1() {
        //declaracion de ventana
        JFrame frame = new JFrame("Nivel 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nivel1 animator = new nivel1();
        
        while(frameNumber == 3 ){
   		 	frameNumber= (int)(Math.random()*7);
   	 	}
        
        //contenido de la ventana.
        frame.add(animator, BorderLayout.CENTER);
        
 
        //mostar la ventana
        frame.pack();
        frame.setVisible(true);
        animator.startAnimation();
        
        System.out.println("finalizar" + finalizar);
        
        while(true){
             System.out.println("finalizar" + finalizar);
        if(finalizar){
        	float[] vector = new float[2];
			vector[0] = (float)numIntentos;
			vector[1] = (float)t_total;
			frame.dispose();
			finalizar = false;
			numIntentos=1;
			t_total=0;

			return vector;
        	}
        }
        

        
    }
 
    public static void main(String[] args) {
       
    	nivel1 NivelFacil= new nivel1();
		float[] res = new float[2];
		res = NivelFacil.iniciarNivel1();
		System.out.println("Intentos: "+res[0]);
		System.out.println("Tiempo: "+res[1]);
    }
}