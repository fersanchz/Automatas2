package arboles;

public class Nodo {
	public Nodo LI;
	public int clave;
	public Nodo LD;
	public Nodo(int clave){
		this.clave=clave;
		LI=null;
		LD=null;		
	}
}
