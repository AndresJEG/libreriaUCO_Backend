package co.edu.uco.libreriauco.pruebas;

import co.edu.uco.libreriauco.dominio.PaisDominio;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilUUID;

public class PruebaBuilder {
	
	public static void main(String [] args) {
		
		PaisDominio dominioPaisUno = new PaisDominio.Builder().build();
		PaisDominio dominioPaisDos = new PaisDominio.Builder().id(UtilUUID.VALOR_DEFECTO).build();
		PaisDominio dominioPaisTres = new PaisDominio.Builder().nombre("C").build();
		PaisDominio dominioPaisCompleto = new PaisDominio.Builder().nombre("C").nombre("DC").id(UtilUUID.VALOR_DEFECTO).build();
		
	}

}
