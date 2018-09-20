package tokens;
import java.util.Vector;
import java.io.*;
public class PruebaToken {
	public static String pReservada[]={"int","boolean","if","false","true","private","public","class"};
	public static String nOperador[][]={{"<=","OPERADOR DE MENOR IGUAL"},{">=","OPERADOR DE MAYOR IGUAL"},{"!=","OPERADOR DE DIFERENTE"},{"==","OPERADOR DE COMPARACION"},{"=","OPERADOR DE ASIGNACION"},{"+","OPERADOR DE SUMA"},{"-","OPERADOR DE RESTA"},{"<","OPERADOR DE MENOR QUE"},{">","OPERADOR DE MAYOR QUE"},{";","OPERADOR FIN DE INSTRUCCION"}};
	public static String nAgrupa[][]={{"(","ABRE PARENTESIS"},{")","CIERRA PARENTESIS"},{"{","ABRE LLAVES"},{"}","CIERRA LLAVES"}};
	public static String alfabetoNumeros="0123456789";
	public static String alfabetoLetras="abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ_";
	
	public static int nToken=0;
	public static Vector<String>token=new Vector<String>();
	public static Vector<String>nomToken=new Vector<String>();
	public static int identificador(String cadena,int pos){
		int nPos=0;
		boolean d=letra(cadena.substring(pos, pos+1));
		if(d)
			nPos=1;
		for(int i=pos+nPos;i<cadena.length()&&d;i++){
			d=letra(cadena.substring(i, i+1));
			if(d){
				nPos++;
			}
		}
		return nPos;
	}
	public static boolean palReservada(String pal){
		boolean r=false;
		for(String p:pReservada){
			if(p.equals(pal)){
				r=true;
			}
		}
		return r;
	}
	public static String nomAgrupa(String cadena){
		String n="";
		for(String op[]:nAgrupa){
			if(op[0].equals(cadena))
				n=op[1];
		}
		return n;
	}
	public static int agrupa(String cadena,int pos){
		int nPos=0;
		boolean d=agrupadores(cadena.substring(pos, pos+1));
		if(d)
			nPos=1;
		return nPos;
	}
	public static boolean agrupadores(String cad){
		boolean existe=false;
		for(String c[]:nAgrupa){
			if(cad.equals(c[0])){
				existe=true;
			}
		}
		return existe;
	}
	public static int signoPuntuacion(String cadena,int pos){
		int nPos=0;
		String alfabeto=";";
		if(alfabeto.contains(cadena.substring(pos, pos+1))){
			nPos++;
		}		
		return nPos;
	}
	public static int numero(String cadena,int pos){
		int nPos=0;
		boolean d=digito(cadena.substring(pos, pos+1));
		if(d)
			nPos=1;
		for(int i=pos+nPos;i<cadena.length()&&d;i++){
			d=digito(cadena.substring(i, i+1));
			if(d){
				nPos++;
			}
		}
		return nPos;
	}
	public static String nomOperador(String cadena){
		String n="";
		for(String op[]:nOperador){
			if(op[0].equals(cadena))
				n=op[1];
		}
		return n;
	}
	public static int operadores(String cadena,int pos){
		int nPos=0;
		boolean d=operador(cadena.substring(pos, pos+1),false);
		for(int i=pos+nPos;i<cadena.length()&&d;i++){
			d=operador(cadena.substring(pos, i+1),false);
			if(d){
				nPos++;
			}
		}
		d=operador(cadena.substring(pos, pos+nPos),true);
		if(!d){
			nPos=0;
		}
		return nPos;
	}
	public static boolean operador(String cadena, boolean esta){
		boolean existe=false;
		for(String c[]:nOperador){
			if((!esta&&c[0].contains(cadena))||(esta&&c[0].equals(cadena))){
				existe=true;
			}
		}
		return existe;
	}
	public static boolean letra(String cadena){
		boolean si=false;
		if(alfabetoLetras.contains(cadena.substring(0, 1))){
			si=true;
		}
		return si;
	}
	public static boolean digito(String cadena){
		boolean si=false;
		if(alfabetoNumeros.contains(cadena.substring(0, 1))){
			si=true;
		}
		return si;
	}
	public static void lXl(String palabras){
		for(int i=0;i<palabras.length();i++){
			int n=signoPuntuacion(palabras,i);
			/*if(signoPuntuacion(palabras,i)>0){
				int fin=signoPuntuacion(palabras,i)+i;
				String tkn=palabras.substring(i, fin);
				token.addElement(tkn);
				nomToken.addElement("SIGNO DE PUNTUACION");
				i=fin-1;
			}else */if(identificador(palabras,i)>0){
				int fin=identificador(palabras,i)+i;
				String pal=palabras.substring(i, fin);
				i=fin-1;
				if(palReservada(pal)){
					token.addElement(pal);
					nomToken.addElement("PALABRA RESERVADA");
				}else{
					token.addElement(pal);
					nomToken.addElement("ID");
				}
			}else if(numero(palabras,i)>0){
				int fin=numero(palabras,i)+i;
				String pal=palabras.substring(i, fin);
				token.addElement(pal);
				nomToken.addElement("ENTERO");
				i=fin-1;
			}else if(operadores(palabras,i)>0){
				int fin=operadores(palabras,i)+i;
				String pal=palabras.substring(i, fin);
				String nOp=nomOperador(palabras.substring(i, fin));
				token.addElement(pal);
				nomToken.addElement(nOp);
				i+=operadores(palabras,i)-1;
			}else if(agrupa(palabras,i)>0){
				int fin=agrupa(palabras,i)+i;
				String pal=palabras.substring(i, fin);
				String nAg=nomAgrupa(palabras.substring(i, fin));
				token.addElement(pal);
				nomToken.addElement(nAg);
				i+=agrupa(palabras,i)-1;
			}else if(palabras.charAt(i)!=' '&&palabras.charAt(i)!='\n'&&palabras.charAt(i)!='\t'){
				String car=palabras.substring(i, i+1);
				token.addElement(car);
				nomToken.addElement("ERROR");
				
			}
		}
	}
	public static boolean classDeclaracion(){
		boolean correcto=true;
		String palabra="";
		if(nToken<=token.size())
			palabra=token.elementAt(nToken);
		if(modificador(palabra)){
			//correcto=true;
			nToken++;
		}
		if(nToken<=token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("class")){
			System.out.println("Error token #"+nToken+": se esperaba la palabra reservada class");
			//correcto=true;
		}
		nToken++;
		String tipoToken="";
		if(nToken<=nomToken.size())
			tipoToken=nomToken.elementAt(nToken);
		if(tipoToken.equals("ID")){
			//correcto=true;
		}else{
			System.out.println("Error token #"+nToken+": se esperaba el nombre de la clase");
			correcto=false;
		}
		nToken++;
		if(nToken<=token.size())
			palabra=token.elementAt(nToken);
		if(palabra.equals("{")){
			//correcto=true;
		}else{
			System.out.println("Error token #"+nToken+": se esperaba apertura de llaves");
			correcto=false;
		}
		nToken++;
		if(nToken<=token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("}")&&(type(palabra)||modificador(palabra))&&field_declaration()){
			//correcto=true;
			//nToken++;
		}
		if(nToken<=token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("}")){
			statement();
				//nToken++;
		}
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(palabra.equals("}")){
			//correcto=true;
		}else{
			System.out.println("Error token #"+nToken+": se esperaba cerrar llaves");
			correcto=false;
		}
		nToken++;
		if(nToken<token.size()){
			System.out.println("Error token #"+nToken+": tokens fuera de la clase");
			correcto=false;
		}
		if(correcto){
			System.out.println("Los toquen estan de acuerdo a la gramatica.");
		}
		return correcto;
	}
	public static boolean field_declaration(){
		boolean correcto=true;
		String palabra="";
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(modificador(palabra)||type(palabra)){
			if(!variable_declaration()){
				correcto=false;
			}
		}
		return correcto;
	}
	public static boolean variable_declaration(){
		boolean correcto=true;
		String palabra="";
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(modificador(palabra)){
			//correcto=true;
			nToken++;
		}
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!type(palabra)){
			System.out.println("Error token #"+nToken+": se esperaba tipo de dato");
			return false;
		}
		nToken++;
		String tipoToken="";
		if(nToken<nomToken.size())
			tipoToken=nomToken.elementAt(nToken);
		if(tipoToken.equals("ID")){
			if(!variable_declarator()){
				System.out.println("Error token #"+nToken+": se esperaba declaracion de variable");
				return false;
			}
		}
		nToken++;
		if(nToken<=token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals(";")){
			System.out.println("Error token #"+nToken+": se esperaba punto y coma");
			correcto=false;
		}
		nToken++;
		return correcto;
	}
	public static boolean variable_declarator(){
		boolean correcto=true;
		String palabra="";
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		String tipoToken="";
		if(nToken<nomToken.size())
			tipoToken=nomToken.elementAt(nToken);
		if(!tipoToken.equals("ID")){
			System.out.println("Error token #"+nToken+": se esperaba un identificador");
			return false;
		}
		nToken++;
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("=")){
			System.out.println("Error token #"+nToken+": se esperaba un operador de asignacion");
			return false;
		}
		nToken++;
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(nToken<nomToken.size())
			tipoToken=nomToken.elementAt(nToken);
		if(!(tipoToken.equals("ENTERO")||palabra.equals("true")||palabra.equals("false"))){
			System.out.println("Error token #"+nToken+": se esperaba un tipo entero o booleano");
			correcto=false;
		}
		return correcto;
	}
	public static boolean statement(){
		boolean correcto=false;
		String palabra="";
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(modificador(palabra)||type(palabra)){
			if(variable_declaration()){
				correcto=true;
				//nToken++;
			}else{
				System.out.println("Error: Se esperaba una declaracion de variable");				
			}
		}
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if((palabra.equals("if"))){
			if(if_statement()){
				correcto=true;
			}else{
				System.out.println("Error: Se esperaba una declaracion if");	
			}
		}
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if((palabra.equals("while"))){
			if(while_statement()){
				correcto=true;
			}else{
				System.out.println("Error: Se esperaba una declaracion de while");	
			}
		}
		return correcto;
	}
	public static boolean while_statement(){
		boolean correcto=true;
		String palabra="";
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("while")){
			return false;
		}
		nToken++;
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("(")){
			System.out.println("Error token #"+nToken+": se esperaba un parentesis abierto");
			return false;
		}
		nToken++;
		if(!expression()){
			System.out.println("Error token #"+nToken+": se esperaba una expresion");
			return false;
		}
		nToken++;
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals(")")){
			System.out.println("Error token #"+nToken+": se esperaba un parentesis cerrado");
			return false;
		}
		nToken++;
		statement();
		return correcto;
	}
	public static boolean if_statement(){
		boolean correcto=true;
		String palabra="";
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("if")){
			return false;
		}
		nToken++;
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals("(")){
			System.out.println("Error token #"+nToken+": se esperaba un parentesis abierto");
			return false;
		}
		nToken++;
		if(!expression()){
			System.out.println("Error token #"+nToken+": se esperaba una expresion");
			return false;
		}
		nToken++;
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!palabra.equals(")")){
			System.out.println("Error token #"+nToken+": se esperaba un parentesis cerrado");
			return false;
		}
		nToken++;
		statement();
		return correcto;
	}
	public static boolean expression(){
		boolean correcto=false;
		String tipoToken="";
		if(nToken<nomToken.size())
			tipoToken=nomToken.elementAt(nToken);
		if((tipoToken.equals("ENTERO")||tipoToken.equals("ID"))){
			if(testing_expression()){
				correcto=true;				
			}else{
				System.out.println("Error: Se esperaba una expresion");
			}
		}
		return correcto;
	}
	public static boolean testing_expression(){
		boolean correcto=true;
		String palabra="";
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		String tipoToken="";
		if(nToken<nomToken.size())
			tipoToken=nomToken.elementAt(nToken);
		if(!(tipoToken.equals("ENTERO")||tipoToken.equals("ID"))){
			return false;
		}
		nToken++;
		if(nToken<token.size())
			palabra=token.elementAt(nToken);
		if(!(palabra.equals(">")||palabra.equals("<")||palabra.equals(">=")||palabra.equals("<=")||palabra.equals("==")||palabra.equals("!="))){
			System.out.println("Error token #"+nToken+": se esperaba un operador de comparacion");
			return false;
		}
		nToken++;
		if(nToken<nomToken.size())
			tipoToken=nomToken.elementAt(nToken);
		if(!(tipoToken.equals("ENTERO")||tipoToken.equals("ID"))){
			System.out.println("Error token #"+nToken+": se esperaba un entero o identificador");
			return false;
		}
		return correcto;
	}
	public static boolean type(String palabra){
		boolean correcto=true;
		if(!type_specifier(palabra)){
			correcto=false;
		}
		return correcto;
	}
	public static boolean type_specifier(String palabra){
		boolean correcto=false;
		if(palabra.equals("boolean")||palabra.equals("int")){
			correcto=true;
		}
		return correcto;
	}
	public static boolean modificador(String t){
		boolean correcto=false;
		if(t.equals("public")||t.equals("private")){
			correcto=true;
		}
		return correcto;
	}
	public static String dameTexto(){
		String linea="",texto="";
		try{
			FileReader archivo=new FileReader("src/tokens/codigo fuente.txt"); // lee el archivo
			BufferedReader leeLinea=new BufferedReader(archivo); // filtra los daros de FileReader
			linea=leeLinea.readLine(); // otorga la primer linea del archivo
			while(linea!=null){ // si en la variable linea se guardo datos(no esta vacia) hara el ciclo
				texto+=linea+"\n";
				linea=leeLinea.readLine(); // lee la siguiente linea
			}
		}catch(FileNotFoundException exception){ // cacha el error de archivo no encontrado
			System.out.println(exception);
		}catch(Exception exception){ // cacha los otros errores
			System.out.println(exception);
		}
		return texto;
	}
	public static void main(String[] args) {
		//String palabras="INICIO\n\tint A;\n\tA=5;\n\twhile(A==5)\n\t{\n\t\tint B; B=6; A=(B+A);\n\t\tprintln A;\n\t};\n\tboolean C;\n\tprintln C;\nFIN\n\n";
		//String palabras="\npublic class Pru_eba{ \n\tint a=0; \n\tboolean si=true; \n\tb=a+1; \n\tif(b>=5) \n\t\twhile(si!=true)\n}\n";
		String palabras=dameTexto();
		System.out.println(palabras);
		lXl(palabras);
		for(int i=0;i<token.size();i++){
			System.out.println("Token #"+(i)+": "+token.elementAt(i)+"\t"+nomToken.elementAt(i));
			
		}
		classDeclaracion();
	}

}
