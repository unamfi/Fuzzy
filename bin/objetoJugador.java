//package org.pamn.proyecto;

public class objetoJugador extends Objeto {
	
	private principal escena;
	
	public objetoJugador (principal escena, String ref, int x, int y, boolean objetivo) {
		super (ref, x, y, objetivo);
		this.escena = escena;
	}
	
	public void desplazar (long delta) {
		if ( ( (vx < 0) && (x < 25) ) || ( (vx > 0) && (x > 590)) ) {
			return;
		}
		
		if ( ( (vy < 0) && (y < 25) ) || ( (vy > 0) && (y > 540)) ) {
			return;
		}
		
		super.desplazar(delta);
	}

}
