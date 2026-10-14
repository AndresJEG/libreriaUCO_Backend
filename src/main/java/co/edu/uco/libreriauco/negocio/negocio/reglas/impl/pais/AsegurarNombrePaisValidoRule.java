package co.edu.uco.libreriauco.negocio.negocio.reglas.impl.pais;

import co.edu.uco.libreriauco.negocio.negocio.reglas.Rule;
import co.edu.uco.libreriauco.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCONegocioExcepcion;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilTexto;

public class AsegurarNombrePaisValidoRule implements Rule <String> {
	
private static final AsegurarNombrePaisValidoRule instancia = new AsegurarNombrePaisValidoRule();
	
	private AsegurarNombrePaisValidoRule() {
		
	}
	
	public static final Rule<String> obtenerInstancia(){
		return instancia;
	}

	@Override
	public void ejecutar(String... datos) {
		var nombrePais = datos[0];
		
		validarFormato(nombrePais);
		validarObligatoriedad(nombrePais);
		validarlongitud(nombrePais, 1, 50);
		
	}
	
	private void validarObligatoriedad (String dato) {
		if(UtilTexto.getUtilTexto().esVacia(dato)) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.NOMBRE_PAIS_OBLIGATORIO;
			throw LibreriaUCONegocioExcepcion.crear(mensajeUsuario);
		}
		
	}
	
	private void validarlongitud(String dato, int longitudMinima, int longitudMaxima) {
		if(UtilTexto.getUtilTexto().longitudCadenaEsValidaProfe(longitudMinima, longitudMaxima, dato, false)) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.LONGITUD_NOMBRE_PAIS_NO_VALIDA;
			throw LibreriaUCONegocioExcepcion.crear(mensajeUsuario);
		}
		
	}
	
	private void validarFormato(String dato) {
		if(!UtilTexto.getUtilTexto().formatoEsValido(dato, UtilTexto.SOLO_LETRAS_ESPACIOS)) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.FORMATO_NOMBRE_PAIS_NO_VALIDO;
			throw LibreriaUCONegocioExcepcion.crear(mensajeUsuario);
		}
		
	}

}
