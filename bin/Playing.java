//UNIVERSIDAD NACIONAL AUTONOMA DE MEXICO.
//FACULTAD DE INGENIERIA.
//MATERIA: SISTEMAS EXPERTOS.
//PROF.: M.I. JAIME ALFONSO REYES CORTES.
//GRUPO: 01.
//CLASE QUE EJECUTA UN CLIP DE SONIDO
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import java.io.*;

public class Playing extends Thread { //implements Runnable{
	private String fileName = new String();
	private static final String name = "Reproducción";
	//private Thread hilo;
	public Playing(String filename)
	{
		//hilo = new Thread(this);
		if(filename != "")
		{
			setName(name);
			fileName = filename;
			System.out.println("Nombre:" + fileName);
			setPriority(Thread.NORM_PRIORITY);
			start();
			
		}
	}
	public Playing()
	{ this(null);}
/*	public ~Playing()
	{
		stop();
	}*/
	public void run()
	{
		play();
	}
	
	
	public synchronized void play()
	{
		try
		{
			//Reproduce el sonido
			Clip playing = AudioSystem.getClip();
			playing.open(AudioSystem.getAudioInputStream(new File(fileName)));
			long l = playing.getMicrosecondLength();//Obtiene la duración del audio
			playing.start();
			while(playing.isRunning())
				;
			
			Thread.sleep(l*1000); //retrasa el Thread para darle tiempo a que termine
			//playing.stop();
			playing.close();
		
		}
		catch(Exception e)
		{
			String err = new String();
			err = e.toString();
			JOptionPane.showMessageDialog(null, err, "Error de ejecución WAV.", JOptionPane.WARNING_MESSAGE);
			//berror = true;
		}
	}
}
