///package org.pamn.proyecto;

public class objetoObstaculo extends Objeto {
	
	private double velMov = 5;
	private principal escena;
	
	public objetoObstaculo(principal escena, String ref, int x, int y, boolean objetivo) {
		super(ref, x, y, objetivo);
		this.escena = escena;
		
	}
	
	public void desplazar( long delta ) {
		if ( (vx > 0) && (x > 550)  )  {
			cambioH = true;
		} 
		if ( (vx < 0) && (x < 25) ) {
			cambioH = false;
		}
		
		if ( (vy > 0) && (y > 540) ) {
			cambioV = true;
		}
		
		if ( (vy < 0) && (y < 25) ) {
			cambioV = false;
		}
				
		super.desplazar(delta);
	}
	
	

	}


