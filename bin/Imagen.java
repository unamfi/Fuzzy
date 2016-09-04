//package org.pamn.proyecto;

import java.awt.Graphics;
import java.awt.Image;

public class Imagen {
	/*  Atributos */
	
	private Image imagen;   //Variable que almacena la clase imagen
    
	/* Constructor */
	
	public Imagen(Image imagen){
		this.imagen = imagen;
	}
	
	/* Metodos */
	
	//Metodo para dibujar la Imagen imagen en la posicion(x,y)
	public void dibujar(Graphics g, int x, int y){
		g.drawImage (imagen, x, y, null);  //En esta linea ponemos la imagen
	}
	
	public int getWidth() {
		return imagen.getWidth(null);
	}
	
	public int getHeight() {
		return imagen.getHeight(null);
	}
	
}

