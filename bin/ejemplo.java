
public class ejemplo 
{	
	public int intentos;
	public float tiempo;
	
	public ejemplo()
	{
		intentos = 4;
		tiempo = 4;
	}
	
	public int getInt()
	{
		return this.intentos;
	}
	
	public float getTie()
	{
		return this.tiempo;
	}
	
	public float[] muestraEstrellas(int estrellas)
	{
		float[] funciona = new float[2];
		funciona[0] = 27.5f;
		funciona[1] = (float) estrellas;
		System.out.println(estrellas + " shine like a diamond");
		return funciona;
	}
	
}
