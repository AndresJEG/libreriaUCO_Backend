package co.edu.uco.libreriauco.transversal.utilitarios;

public class UtilTexto {
	private static UtilTexto INSTANCIA;
	public static String VACIA = "";
	public static final String SOLO_LETRAS_ESPACIOS ="^[a-zA-Z ñÑáÁÉéíÍóÓúÚ]*$";
	
	private UtilTexto(){
	}
	
	public static UtilTexto getUtilTexto() {
		
		synchronized (UtilTexto.class) {
			if (UtilObjeto.esNulo(INSTANCIA)) {
				INSTANCIA = new UtilTexto();
			}
		}
		return INSTANCIA;
	}
	
	public boolean esNula(String cadena) {
		return UtilObjeto.esNulo(cadena);
	}
	
	public boolean esVacia(String cadena) {
		return VACIA.equals(obtenerValorDefecto(cadena));
	}
	
	public String obtenerValorDefecto(String valor, String valorDefecto) {
		return UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(valor, valorDefecto);
	}
	
	public String obtenerValorDefecto(String valor) {
		return obtenerValorDefecto(valor,VACIA);
	}
	
	public String quitarEspaciosEnBlanco(String valor) {
		return obtenerValorDefecto(valor).trim();
	}
	
	public int obtenerLongitudCadena(String Valor) {
		return obtenerValorDefecto(Valor).length();
	}
	
	public int obtenerLongitudCadena(String valor, boolean quitarEspaciosBlanco) {
		
		return quitarEspaciosBlanco ? obtenerLongitudCadena(quitarEspaciosEnBlanco(valor)): 
			obtenerLongitudCadena(valor);
		
	}
	
	public boolean longitudCadenaEsValida(int longitudInicial, int longitudFinal, String valor, boolean quitarEspaciosBlanco) {
		return obtenerLongitudCadena(valor, quitarEspaciosBlanco) >= longitudInicial && 
				obtenerLongitudCadena(valor,quitarEspaciosBlanco) <= longitudFinal;
	}
	
	public boolean longitudCadenaEsValidaProfe(int longitudInicial, int longitudFinal, String valor, boolean quitarEspaciosBlanco) {
		
		var valorSanitizado = quitarEspaciosBlanco ? quitarEspaciosEnBlanco(valor) : valor;
		
		return obtenerLongitudCadena(valorSanitizado) >= longitudInicial && 
				obtenerLongitudCadena(valorSanitizado) <= longitudFinal;
	}
	
	public boolean formatoEsValido(String valor, String patron) {
		return obtenerValorDefecto(valor).matches(obtenerValorDefecto(patron));
	}
	
	public final String concatenar(final String... textos) {
        if (textos == null) {
            return VACIA;
        }
        
        final StringBuilder sb = new StringBuilder();
        for (final String texto : textos) {
            sb.append(obtenerValorDefecto(texto));
        }
        return sb.toString();
    }

}
