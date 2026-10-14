package co.edu.uco.libreriauco.negocio.negocio.reglas.impl.pais;

import co.edu.uco.libreriauco.dao.factoria.DAOFactory;
import co.edu.uco.libreriauco.entidad.PaisEntidad;
import co.edu.uco.libreriauco.negocio.negocio.reglas.Rule;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCONegocioExcepcion;

public class AsegurarNombreNuevoPaisNoExistaRule implements Rule <Object> {
	
	private static final AsegurarNombreNuevoPaisNoExistaRule instancia = new AsegurarNombreNuevoPaisNoExistaRule();
	
	private AsegurarNombreNuevoPaisNoExistaRule() {
		
	}
	
	public static final Rule<Object> obtenerInstancia(){
		return instancia;
	}

	@Override
	public void ejecutar(Object... datos) {
		//OJO CO EL ORDEN
		
		var nombrePais = (String) datos[0]; //se llama casteo, estoy seguro que en esta posicion llega un string
		var daoFactory = (DAOFactory) datos[1];
		
		
		
		var entidadFiltro = new PaisEntidad();
		entidadFiltro.setNombre(nombrePais.trim());

		var resultados = daoFactory.obtenerPaisDAO().consultarPorFiltro(entidadFiltro);

		boolean existeCoincidenciaExacta = resultados.stream()
				.anyMatch(p -> p.getNombre() != null && p.getNombre().trim().equalsIgnoreCase(nombrePais.trim()));

		if (existeCoincidenciaExacta) {
			throw LibreriaUCONegocioExcepcion.crear(
					"Ya existe un país registrado con el nombre '" + nombrePais.trim() + "'."
			);
		}
		
	}

}
