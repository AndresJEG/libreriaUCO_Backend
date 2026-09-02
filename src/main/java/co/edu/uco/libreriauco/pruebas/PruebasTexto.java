package co.edu.uco.libreriauco.pruebas;

import static co.edu.uco.libreriauco.transversal.utilitarios.UtilTexto.getUtilTexto;

import java.time.LocalDate;
import java.util.UUID;

import co.edu.uco.libreriauco.transversal.utilitarios.UtilFecha;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilUUID;

public class PruebasTexto {

	public static void main(String[] args) {
		
		String miVarible = "  mi casa es azul     ";
		
		LocalDate fechaPrueba = null;
		
		UUID idPrueba = null;
		
		System.out.println(miVarible);
		
		System.out.println(getUtilTexto().obtenerLongitudCadena(miVarible, false));
		System.out.println(getUtilTexto().obtenerLongitudCadena(miVarible, true));
		
		System.out.println("Aca la prueba de fecha antes de aplicar antinulo");
		System.out.println(fechaPrueba);
		
		System.out.println("Aca la prueba del id antes de aplicar antinulo");
		System.out.println(idPrueba);
		
		System.out.println("Aca la prueba de fecha aplicando antinulo");
		System.out.println(UtilFecha.obtenerValorDefecto(fechaPrueba));
		
		System.out.println("Aca la prueba del id aplicando antinulo");
		System.out.println(UtilUUID.obtenerValorDefecto(idPrueba));

	}
	
	

}
