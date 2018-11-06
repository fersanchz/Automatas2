package Expresion;
import java.util.StringTokenizer;
import java.util.Vector;
public class Arbol {
	public  String [][]asOperadores={
			{"/","0"},
			{"*","0"},
			{"+","1"},
			{"-","1"}
	};
	public  Nodo raiz=null;
	public Arbol(Vector<String> datos){
		llenaArbol(datos);
	}
	public boolean esOperador(String cadena){
		boolean si=false;
		for(String []sOperador:asOperadores){
			if(sOperador[0].equals(cadena))
				si=true;
		}
		return si;
	}
	public  int valorOperador(String sOp){
		int valor=-1;
		for(String []sOperador:asOperadores){
			if(sOperador[0].equals(sOp)){
				valor=Integer.parseInt(sOperador[1]);
			}
		}
		return valor;
	}
	public  boolean esMayor(String sOp,String sOp2){
		boolean mayor=false;
		int valor1=valorOperador(sOp);
		int valor2=valorOperador(sOp2);
		if(valor1<valor2){
			mayor=true;
		}
		return mayor;
	}
	public boolean InsertaABB(String clave,Nodo nodo){
		if(esOperador(clave)){
			if(nodo.LD==null||!esOperador(nodo.LD.clave)){
				Nodo nuevo=new Nodo(clave);
				nuevo.LI=nodo.LD;
				nodo.LD=nuevo;
				return true;
			}else if(esOperador(nodo.LD.clave)&&!esMayor(clave,nodo.LD.clave)){
				Nodo nuevo=new Nodo(clave);
				nuevo.LI=nodo.LD;
				nodo.LD=nuevo;
				return true;
			}else{
				InsertaABB(clave,nodo.LD);
			}
		}else{
			if(nodo.LI==null){
				Nodo nuevo=new Nodo(clave);
				nuevo.LI=nuevo;
				return true;
			}else if(nodo.LD==null){
				Nodo nuevo=new Nodo(clave);
				nodo.LD=nuevo;
				return true;
			}else{
				InsertaABB(clave,nodo.LD);
			}
		}
		return false;
	}

	public void inorden(Nodo nodo){ 
		if (nodo!=null){
			inorden(nodo.LI);
			System.out.print(" "+nodo.clave);
			inorden(nodo.LD);
		}
	}
	public void muestraInorden(){
		inorden(raiz);
	}
	public void preorden(Nodo nodo){
		if (nodo!=null){
			System.out.print(" "+nodo.clave);
			preorden(nodo.LI);
			preorden(nodo.LD);
		}
	}
	public void muestraPreorden(){
		preorden(raiz);
	}
	public void postorden(Nodo nodo){
		if (nodo!=null){
			postorden(nodo.LI);
			postorden(nodo.LD);
			System.out.print(" "+nodo.clave);
		}
	}
	public void muestraPostorden(){
		postorden(raiz);
	}
	public void llenaArbol(Vector<String> datos){
		for(int i=0;i<datos.size();i++){
			String valor=datos.elementAt(i);
			if(raiz==null)
				raiz=new Nodo(valor);
			else{
				if(esOperador(raiz.clave)){
					if (esOperador(valor)&&!esMayor(valor,raiz.clave))
					{
						Nodo nuevo=new Nodo(valor);
						nuevo.LI=raiz;
						raiz=nuevo;
					}else
						InsertaABB(valor,raiz);
				}else{
					Nodo nuevo=new Nodo(valor);
					nuevo.LI=raiz;
					raiz=nuevo;
				}
			}
		}
	}
	public static void main(String[] args) {
		//String sExpresion="A + B / D * E";
		String sExpresion="A + B * C";
		StringTokenizer tokens= new StringTokenizer(sExpresion);
		Vector<String> datos = new Vector<String>();
		while(tokens.hasMoreTokens()){
			datos.add(tokens.nextToken());
	    }
		Arbol arbol=new Arbol(datos);
		System.out.print("\nInorden:\t");
		arbol.muestraInorden();
		System.out.print("\nPreorden:\t");
		arbol.muestraPreorden();
		System.out.print("\nPostorden:\t");
		arbol.muestraPostorden();
	}

}
