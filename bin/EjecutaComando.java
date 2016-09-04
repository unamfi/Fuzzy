import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import java.io.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.JOptionPane;

public class EjecutaComando
{
public static boolean EjecutaComando(String comando)
	{
		boolean berror = false;
		String aux = new String();
		try
		{
			
		   Process pr = Runtime.getRuntime().exec (new String[] {
			        "chmod",
			        "u"+'+'+"x",
			        comando,
			    }); //"cmd /c "+
		   Runtime.getRuntime().exec (comando);
		}
		catch (Exception e)
		{
		   //Se lanza una excepción si no se encuentra en ejecutable o el fichero no es ejecutable. 
			String err = new String();
			err = e.toString();
			System.out.println(err);
		}
		return berror;
	}

//	public static void main(String args[])
//	{
//		try{
//		EjecutaComando.EjecutaComando("open /Users/JulioCesar/Desktop/Fuzzy/bin/TutoElefante.swf");
//		System.out.print("Lo logrŽ");
//		}
//		catch (Exception e)
//		{
//		   /* Se lanza una excepción si no se encuentra en ejecutable o el fichero no es ejecutable. */
//			String err = new String();
//			err = e.toString();
//			System.out.println(err);
//		}
//	}

}

	