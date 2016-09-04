//package org.pamn.proyecto;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.PopupMenu;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
/*
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
*/
public class principal extends Canvas implements Runnable{
	
	//VARS TIMER

	private static final long serialVersionUID = 1L;

	private Label etiqueta;
	private long tiempoInicial = 0;
	long tiempoInicio;
	long totalTiempo=0;
	//public int 
	
	
	
	//VARS VENTANA
	private final Color valoresColor[]={Color.black,Color.blue,Color.red,Color.green }; 
    private JRadioButtonMenuItem elementosColor[], tiposLetra[]; 
    private JCheckBoxMenuItem elementosEstilo[]; 
    private JLabel pantallaEtiqueta; 
    private ButtonGroup grupoTiposLetra, grupoColores; 
    private int estilo; 
	
	/* VARIABLES */
	private static final int VENTANA_ANCHO = 800;
	private static final int VENTANA_ALTO = 600;
	
	private BufferStrategy s;	
	private ArrayList elementos = new ArrayList();	//Contenedor de los objetos
	
	private Objeto jugador;
	private double vel = 300;	//Velocidad del jugador
	private Objeto enemigo;
	
	private boolean cicloJuego = true;
	
	public float tiempo1;
	
	//Teclas de interacción
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
		
	//Julio
	
	public int intentos = 1;
	public JFrame ventana;
	/* METODOS */
	
	//Constructor principal
	public principal() {
        
        EjecutaComando.EjecutaComando("open /Users/JulioCesar/Desktop/Fuzzy/bin/TutoNave.swf");
        
        try{
            Thread.sleep(10000);
        }
        catch(InterruptedException e)
        {
            
        }
        
		crearVentana(VENTANA_ANCHO, VENTANA_ALTO);
		inicializaElementos();
		System.out.println("Inicia el juego");
	}
	/*
	public void tweetCom() {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("mYOvUCIHBuVqikgwwmG2A", "AXmNEoKVr82tVeEeF2q0znv787WSTsWWJuWJY8Q");
			RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = null;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while( null == accessToken){
				System.out.println("URL de acceso: ");
				System.out.println(requestToken.getAuthenticationURL());
				System.out.println("PIN: ");
				String pin = br.readLine();
				try {
					if( pin.length() > 0 ) {
						accessToken = twitter.getOAuthAccessToken(requestToken, pin);
					}
					else {
						accessToken = twitter.getOAuthAccessToken();
					}
				} catch(TwitterException te) {
					if ( 401 == te.getStatusCode()) {
						System.out.println("Error al obtener el access token");
					} else {
						te.printStackTrace();
					}
				}
			}
			
			Status status = twitter.updateStatus("MENSAJE DE PRUEBA");
			System.out.println("Estado actualizado a: " + status.getText());
			System.exit(0);			
		} catch(TwitterException te) {
			System.out.println("Error al obtener el timeline: " + te.getMessage());
			System.exit(-1);
		} catch(IOException e) {
			System.out.println("Error en la entrada");
			System.exit(-1);
		}
		
	}
	*/
	//Creación de la ventana
	public void crearVentana(int x, int y) {
		//Crear la ventana "TankeWars"
		this.ventana = new JFrame("Nivel 2");
		JPanel lienzo = (JPanel) ventana.getContentPane();
		
		//Establece las dimensiones de la ventana principal 
		lienzo.setPreferredSize(new Dimension(VENTANA_ANCHO, VENTANA_ALTO));
		//La posición de cada objeto será definida por el usuario(setLayout)
		lienzo.setLayout(null);
		
		setBounds(0, 0, VENTANA_ANCHO, VENTANA_ALTO);
		lienzo.add(this);
		//Al usar rendering en 2D, no es necesario que Java redibuje constantemente
		//la ventana principal
		setIgnoreRepaint(true);
		//Agregando el lienzo a la ventana principal con las propiedades indicadas
		ventana.pack();
		ventana.setResizable(false); 	//No cambia de tamaño
		ventana.setVisible(true);		//Hacer a la ventana visible
		mensaje("[Sistema]Info: Ventana desplegada");	//Debug
		
		//Evento escucha para terminar y salir del sistema al cerrar la ventana
		ventana.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				mensaje("[SIstema]Info: Ventana cerrada, saliendo del programa");  ///Saliendo del sistema
				
				
				System.exit(0);
			}
			
		});
		
		addKeyListener(new KeyInputHandler());
		
		//SOlicitar que la ventana este en el foco principal del usuario
		requestFocus(true);
		//Crear el buffer que despliega los graficos
		createBufferStrategy(2);
		s = getBufferStrategy();
	}
	
	//Ciclo de desplegado de graficos
	public float[] cicloPrincipal() {
		long ultimoCiclo = System.currentTimeMillis();
		long delta;
		
		//Ejecución del ciclo de dibujado de graficos
		mensaje("[Sistema]Info: Ejecutando ciclo principal");
		tiempoInicio = System.currentTimeMillis();  //Se toma el tiempo al iniciar ejecucion
	
		
		while(cicloJuego) {
			Graphics2D g = (Graphics2D)s.getDrawGraphics();
			try {
				delta = System.currentTimeMillis() - ultimoCiclo;
				ultimoCiclo = System.currentTimeMillis();
				//Establece el color (BLACK)
				g.setColor(Color.DARK_GRAY);
				//Dibuja la superficie con el color indicado desde (x0,y0) hasta (x1,y1)
				//g.fillRect(x0, y0, x1, y1);
				g.fillRect(0, 0, VENTANA_ANCHO, VENTANA_ALTO);
				
				g.setColor(Color.WHITE);
				g.fillRect(20, 20, VENTANA_ANCHO - 200, VENTANA_ALTO - 50);
				
				for(int i = 0; i < elementos.size(); i++) {
					Objeto objetos = (Objeto)elementos.get(i);
					objetos.desplazar(delta);
				}
				
				for(int i = 0; i < elementos.size(); i++) {
					Objeto objetos = (Objeto)elementos.get(i);
					objetos.dibujar(g);
				}
				
				//for(int m = 0; m < elementos.size(); m++) {
				Objeto yo = (Objeto) elementos.get(0);
				
				for( int n = 1; n < elementos.size(); n++) {
					Objeto el = (Objeto) elementos.get(n);
					
				
					if( yo.chocaCon(el) == true && el.objetivo ) {
						
						cicloJuego = false;//Termina el juego
						
						//JOptionPane.showMessageDialog(null,"Ha terminado el juego", "Fin del juego", JOptionPane.DEFAULT_OPTION);
						//tweetCom();
						System.out.println("Finaliza el juego");
						totalTiempo = (System.currentTimeMillis() - tiempoInicio)/1000;
						System.out.println("El tiempo de demora es: " + totalTiempo + "seg");
						
						
						//System.exit(0);
						ventana.setVisible(false);
						float[] vector = new float[2];
						vector[0] = intentos;
						vector[1] = totalTiempo;
						return vector;
						
						//System.out.println("popis");
					}	
					if( yo.chocaCon(el) == true && !el.objetivo )
					{
						intentos++;
						
						jugador.x = 300;
						jugador.y = 500;
					}
				}					
								
				for(int m = 1; m < elementos.size(); m++) {
					for( int n = m + 1; n < elementos.size(); n++) {
						Objeto el = (Objeto) elementos.get(m);
						Objeto otro = (Objeto) elementos.get(n);
						
						if( el.chocaCon(otro) == true ) {
							//mensaje("CE");
							if(el.cambioH = false) {
								el.cambioH = true;
								otro.cambioH = false;
							} else {
								el.cambioH = false;
								otro.cambioH = true;
							}						
							
							if(el.cambioV = false) {
								el.cambioV = true;
								otro.cambioV = false;
							} else {
								el.cambioV = false;
								otro.cambioV = true;
							}
						}
						
						if( otro.chocaCon(el) == true ) {
							//mensaje("CE");
							if(otro.cambioH = false) {
								otro.cambioH = true;
								el.cambioH = false;
							} else {
								otro.cambioH = false;
								el.cambioH = true;
							}						
							
							if(otro.cambioV = false) {
								otro.cambioV = true;
								el.cambioV = false;
							} else {
								otro.cambioV = false;
								el.cambioV = true;
							}
						}	
					}
				}
								
				//Libera los graficos creados en pantalla y libera memoria
				g.dispose();
				s.show();
				
				jugador.desplazaHorizontal(0);
				jugador.desplazaVertical(0);
			
				for(int k = 1; k < elementos.size(); k++) {
					Objeto enemigos = (Objeto) elementos.get(k);
					if( enemigos.cambioH == false) {
						enemigos.desplazaHorizontal(200);
					} else {
						enemigos.desplazaHorizontal(-60);
					}
					if( enemigos.cambioV == false) {
						enemigos.desplazaVertical(60);
					} else {
						enemigos.desplazaVertical(-150);
					}
					
				}
				
				if((leftPressed) &&(!rightPressed) && (!upPressed) && (!downPressed)) {
					jugador.desplazaHorizontal(-vel);
				}
				
				if((!leftPressed) &&(rightPressed) && (!upPressed) && (!downPressed)) {
					jugador.desplazaHorizontal(vel);
				}
				
				if((!leftPressed) &&(!rightPressed) && (upPressed) && (!downPressed)) {
					jugador.desplazaVertical(-vel);
				}
				
				if((!leftPressed) &&(!rightPressed) && (!upPressed) && (downPressed)) {
					jugador.desplazaVertical(vel);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				g.dispose();
				
			}
		}
		//System.out.println("El tiempo de demora es :" + totalTiempo + " miliseg");
		//return totalTiempo;
		float[] vector = new float[2];
		vector[0] = 0;
		vector[1] = 0;
		return vector;
	}
	
	//Crea los objetos en la escena, define la imagen que los representará y
	//define la posicion en pantalla
	private void inicializaElementos() {
		jugador = new objetoJugador(this, "imagenes/ovni22.png" , 300, 500, false);
		elementos.add(jugador);
		
		Random r = new Random();
		r.setSeed(new Date().getTime());
		
		for(int i = 0; i < 1; i++) {
			enemigo = new objetoObstaculo(this, "imagenes/troll.png", 100 + 50*i, 300, true);
			elementos.add(enemigo);
		}
		for(int i = 0; i < 1; i++) {
			enemigo = new objetoObstaculo(this, "imagenes/nave_grande.png", 100 + 50*i, 70, false);
			elementos.add(enemigo);
		}
		for(int i = 0; i < 1; i++) {
			enemigo = new objetoObstaculo(this, "imagenes/nave_mediana.png", 100 + 50*i, 70,false);
			elementos.add(enemigo);
		}
		for(int i = 0; i < 1; i++) {
			enemigo = new objetoObstaculo(this, "imagenes/Tanque abajo.png", 100 + 50*i, 70, false);
			elementos.add(enemigo);
		}
		for(int i = 0; i < 1; i++) {
			enemigo = new objetoObstaculo(this, "imagenes/Tanque abajo_grande.png", 100 + 50*i, 70, false);
			elementos.add(enemigo);
		}
		for(int i = 0; i < 1; i++) {
			enemigo = new objetoObstaculo(this, "imagenes/Tanque abajo_mediano.png", 100 + 50*i, 70, false);
			elementos.add(enemigo);
		}
		for(int i = 0; i < 1; i++) {
			enemigo = new objetoObstaculo(this, "imagenes/ovni13.png", 100 + 50*i, 100, false);
			elementos.add(enemigo);
		}
//		for(int i = 0; i < 1; i++) {
//			enemigo = new objetoObstaculo(this, "imagenes/roca32.png", 100 + 50*i, 100);
//			elementos.add(enemigo);
//		}
		

	}
	
	//Procedimiento de mensajes en consola
	public void mensaje(String mensaje) {
		System.out.println(mensaje);	
	}
	
	//Clase que detecta eventos de entrada del teclado
	private class KeyInputHandler extends KeyAdapter {
		//Tecla presionada
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
				mensaje("[Jugador 1]Acción: Tecla izquierda presionada");
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
				mensaje("[Jugador 1]Acción: Tecla derecha presionada");
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
				mensaje("[Jugador 1]Acción: Tecla arriba presionada");
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
				mensaje("[Jugador 1]Acción: Tecla abajo presionada");
			}
		}
		
		//Tecla liberada
	public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
		}
	}	
	
	
	@Override
	public void run() {
		System.out.println("Finaliza el juego");
		Calendar tiempo = Calendar.getInstance();
		while (true) {
		tiempo.setTimeInMillis( System.currentTimeMillis() - tiempoInicial );
		etiqueta.setText(
			tiempo.get( Calendar.MINUTE ) +
			":" +
			tiempo.get( Calendar.SECOND ) +
			":" +
			tiempo.get( Calendar.MILLISECOND )
		);
		
		}
	}

	public void init() {
		float TiempoFinal;
		etiqueta = new Label();

		tiempoInicial = System.currentTimeMillis();
		Thread hilo = new Thread( this );
		hilo.start();
		//mensaje("TiempoFinal");
	}
	
	/*public static void main(String[] args) {
		
		//principal juego = new principal();
		principal NivelDificil= new principal();
		float[] res = new float[2];
		res = NivelDificil.cicloPrincipal();
		System.out.println("Intentos: "+res[0]);
		System.out.println("Tiempo: "+res[1]);
	}*/
}
