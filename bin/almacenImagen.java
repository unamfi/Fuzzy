//package org.pamn.proyecto;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class almacenImagen {
	/* Constructores */
	private static almacenImagen inst= new almacenImagen(); //Se creo un objeto de tipo GuaradaImagen
	private HashMap mapaImagen= new HashMap(); //Se crea otro objeto (guarda referencia);
	
	/* Metodos */
	
	public static almacenImagen get(){
		return inst;
	}
	
	public Imagen obtenImagen(String ref){
		if(mapaImagen.get(ref)!=null){
			return (Imagen) mapaImagen.get(ref);
		}
		BufferedImage ImagenFuente = null;
		
		 try{
			 URL direccion = this.getClass().getClassLoader().getResource(ref);
			 if(direccion == null){
				 System.out.println("--ERROR-- NO SE ENCUENTRA LA IMAGEN" + ref);	 
			 }
			 else {
				 ImagenFuente= ImageIO.read(direccion);  // En esta linea asignamos la imagen leida a esta biblioteca
			 }
		 }catch(IOException e){
			 System.out.println("--ERROR-- NO SE ENCUENTRA LA IMAGEN" + ref);	 
		 }
		 //Creamos una imagen optimizada de la imagen que  indicamos
		 //Obtenemos las propiedades de pantalla del dispositivo
		 GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		 Image imagen = gc.createCompatibleImage(ImagenFuente.getWidth(),ImagenFuente.getHeight(), Transparency.BITMASK); //Creamos una imagen optimizada con las propiedades de la pantalla
		 //Dibujamos nuestra imagen fuente en la imagen optimizada
		 imagen.getGraphics().drawImage(ImagenFuente, 0, 0, null);
		 
		//Creamos una nueva imagen con la que optimizamos en esta clase con el constructor que lla teniamos.
		 Imagen nuevaImagen = new Imagen(imagen);  
		 
         mapaImagen.put(ref, nuevaImagen);
         return(nuevaImagen);
	}

}

