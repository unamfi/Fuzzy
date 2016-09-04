//package org.pamn.proyecto;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Objeto {
	
	protected Imagen imagen;
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	public boolean objetivo;
	
	private Rectangle yo = new Rectangle();
	private Rectangle el = new Rectangle();
	
	public boolean cambioH = false;
	public boolean cambioV = false;
	
	//Carga la imagen "ref" en la posicion en pantalla (x.y)
	public Objeto(String ref, int x, int y, boolean objetivo ) {
		this.imagen = almacenImagen.get().obtenImagen(ref);
		this.x = x;
		this.y = y;
		this.objetivo = objetivo;
	}
	
	//Dibuja en el entorno grafico "g" la imagen en la posicion (x,y)
	public void dibujar (Graphics g) {
		imagen.dibujar (g, (int) x, (int) y);
	}
	
	//Desplaza la imagen en delta unidades
	public void desplazar (long delta) {
		x += (delta * vx) / 1000;
		y += (delta * vy) / 1000;
	}
	
	//Velocidad en el plano x
	public void desplazaHorizontal (double vx) {
		this.vx = vx;
	}
	
	//Velocidad en el plano y
	public void desplazaVertical (double vy) {
		this.vy = vy;
	}

	public boolean chocaCon(Objeto otro) {
		yo.setBounds( (int)x, (int)y, imagen.getWidth(), imagen.getHeight() );
		el.setBounds( (int)otro.x, (int)otro.y, otro.imagen.getWidth(), otro.imagen.getHeight());
		return yo.intersects(el);
	}

}
