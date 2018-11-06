package Expresion;

public class Nodo {
	public Nodo LI;
	public String clave;
	public Nodo LD;
	public Nodo(String clave){
		this.clave=clave;
		LI=null;
		LD=null;		
	}
}
