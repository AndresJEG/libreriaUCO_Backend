package co.edu.uco.libreriauco.negocio.negocio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.libreriauco.dao.factoria.DAOFactory;
import co.edu.uco.libreriauco.dominio.PaisDominio;
import co.edu.uco.libreriauco.entidad.PaisEntidad;
import co.edu.uco.libreriauco.negocio.negocio.PaisNegocio;
import co.edu.uco.libreriauco.negocio.negocio.assembler.impl.PaisEntidadAssembler;
import co.edu.uco.libreriauco.negocio.negocio.reglas.impl.pais.AsegurarNombreNuevoPaisNoExistaRule;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCOExcepcion;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCONegocioExcepcion;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilObjeto;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilTexto;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilUUID;

public class PaisNegocioImpl implements PaisNegocio {

	private final DAOFactory daoFactory;

	public PaisNegocioImpl(DAOFactory daoFactory) {
		if (UtilObjeto.esNulo(daoFactory)) {
			throw LibreriaUCONegocioExcepcion.crear(
					"No es posible crear una instancia de PaisNegocioImpl con una factoría DAO nula.",
					"Se recibió daoFactory nulo en el constructor de PaisNegocioImpl."
			);
		}
		this.daoFactory = daoFactory;
	}

	@Override
	public void registrarInformacionNuevoPais(PaisDominio datos) {

		asegurarDatosRegistroNuevoPaisValidos(datos);
		AsegurarNombreNuevoPaisNoExistaRule.obtenerInstancia().ejecutar(datos.getNombre(), daoFactory); //OJO ACAAA, CADA POLITICA SE COVIERTE EN UNA RULE

		var idUnico = generarIdPaisUnico();
		var datosFinales = new PaisDominio.Builder()
				.id(idUnico)
				.nombre(datos.getNombre().trim())
				.build();

		var paisEntidad = PaisEntidadAssembler.getInstance().convertirAEntidad(datosFinales);
		daoFactory.obtenerPaisDAO().crear(paisEntidad);
	}

	@Override
	public void modificarInformacionPaisExistente(UUID id, PaisDominio datos) {
		try {
			daoFactory.iniciarTransacción();

			asegurarIdValido(id);
			asegurarPaisExistePorId(id);
			asegurarDatosRegistroNuevoPaisValidos(datos);
			asegurarNombrePaisModificadoNoExisteEnOtroPais(id, datos.getNombre());

			var datosFinales = new PaisDominio.Builder()
					.id(id)
					.nombre(datos.getNombre().trim())
					.build();

			var paisEntidad = PaisEntidadAssembler.getInstance().convertirAEntidad(datosFinales);
			daoFactory.obtenerPaisDAO().actualizar(id, paisEntidad);

			daoFactory.confirmarTransacción();
		} catch (LibreriaUCOExcepcion excepcion) {
			daoFactory.cancelarTransacción();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransacción();
			throw LibreriaUCONegocioExcepcion.crear(
					"Se ha presentado un problema inesperado al intentar actualizar la información del país.",
					"Ocurrió una excepción no controlada al modificar la información del país.",
					excepcion
			);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void darBajaInformacionPaisExistente(UUID id) {
		try {
			daoFactory.iniciarTransacción();

			asegurarIdValido(id);
			asegurarPaisExistePorId(id);

			daoFactory.obtenerPaisDAO().eliminar(id);

			daoFactory.confirmarTransacción();
		} catch (LibreriaUCOExcepcion excepcion) {
			daoFactory.cancelarTransacción();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransacción();
			throw LibreriaUCONegocioExcepcion.crear(
					"Se ha presentado un problema inesperado al intentar eliminar la información del país.",
					"Ocurrió una excepción no controlada al dar de baja el país.",
					excepcion
			);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public List<PaisDominio> consultarPorFiltro(PaisDominio filtro) {
		try {
			var filtroDominio = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
					filtro, new PaisDominio.Builder().build()
			);
			var filtroEntidad = PaisEntidadAssembler.getInstance().convertirAEntidad(filtroDominio);

			var resultadosEntidades = daoFactory.obtenerPaisDAO().consultarPorFiltro(filtroEntidad);
			List<PaisDominio> resultadosDominio = new ArrayList<>();

			for (PaisEntidad entidad : resultadosEntidades) {
				resultadosDominio.add(PaisEntidadAssembler.getInstance().convertirADominio(entidad));
			}

			return resultadosDominio;
		} catch (LibreriaUCOExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw LibreriaUCONegocioExcepcion.crear(
					"Se ha presentado un problema inesperado al intentar consultar los países con el filtro especificado.",
					"Ocurrió una excepción no controlada al consultar por filtro.",
					excepcion
			);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public List<PaisDominio> consultarTodos() {
		try {
			var resultadosEntidades = daoFactory.obtenerPaisDAO().consultarTodos();
			List<PaisDominio> resultadosDominio = new ArrayList<>();

			for (PaisEntidad entidad : resultadosEntidades) {
				resultadosDominio.add(PaisEntidadAssembler.getInstance().convertirADominio(entidad));
			}

			return resultadosDominio;
		} catch (LibreriaUCOExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw LibreriaUCONegocioExcepcion.crear(
					"Se ha presentado un problema inesperado al intentar consultar todos los países.",
					"Ocurrió una excepción no controlada al consultar todos los países.",
					excepcion
			);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public PaisDominio consultarPorID(UUID id) {
		try {
			asegurarIdValido(id);
			var entidad = daoFactory.obtenerPaisDAO().consultarPorId(id);

			if (UtilObjeto.esNulo(entidad)) {
				return null;
			}

			return PaisEntidadAssembler.getInstance().convertirADominio(entidad);
		} catch (LibreriaUCOExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw LibreriaUCONegocioExcepcion.crear(
					"Se ha presentado un problema inesperado al intentar consultar el país por ID.",
					"Ocurrió una excepción no controlada al consultar por ID.",
					excepcion
			);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	// =========================================================================
	// Métodos Privados de Validación y Reglas de Negocio
	// =========================================================================

	private void asegurarDatosRegistroNuevoPaisValidos(PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw LibreriaUCONegocioExcepcion.crear(
					"Los datos del país no pueden estar vacíos."
			);
		}
		validarNombrePais(datos.getNombre());
	}

	private void validarNombrePais(String nombre) {
		if (UtilObjeto.esNulo(nombre) || nombre.isBlank()) {
			throw LibreriaUCONegocioExcepcion.crear(
					"El nombre del país es obligatorio y no puede estar vacío."
			);
		}

		if (!nombre.equals(nombre.trim())) {
			throw LibreriaUCONegocioExcepcion.crear(
					"El nombre del país no debe contener espacios al inicio ni al final."
			);
		}

		var nombreLimpio = nombre.trim();
		if (nombreLimpio.length() < 3 || nombreLimpio.length() > 50) {
			throw LibreriaUCONegocioExcepcion.crear(
					"El nombre del país debe tener entre 3 y 50 caracteres."
			);
		}
	}


	private void asegurarNombrePaisModificadoNoExisteEnOtroPais(UUID idActual, String nombreNuevo) {
		var entidadFiltro = new PaisEntidad();
		entidadFiltro.setNombre(nombreNuevo.trim());

		var resultados = daoFactory.obtenerPaisDAO().consultarPorFiltro(entidadFiltro);

		boolean existeOtroPaisConMismoNombre = resultados.stream()
				.anyMatch(p -> p.getNombre() != null 
						&& p.getNombre().trim().equalsIgnoreCase(nombreNuevo.trim())
						&& !p.getId().equals(idActual));

		if (existeOtroPaisConMismoNombre) {
			throw LibreriaUCONegocioExcepcion.crear(
					"Ya existe otro país registrado con el nombre '" + nombreNuevo.trim() + "'."
			);
		}
	}

	private void asegurarPaisExistePorId(UUID id) {
		var entidadExistente = daoFactory.obtenerPaisDAO().consultarPorId(id);
		if (UtilObjeto.esNulo(entidadExistente)) {
			throw LibreriaUCONegocioExcepcion.crear(
					"El país con el identificador ingresado no existe en el sistema."
			);
		}
	}

	private void asegurarIdValido(UUID id) {
		if (UtilObjeto.esNulo(id) || id.equals(UtilUUID.VALOR_DEFECTO)) {
			throw LibreriaUCONegocioExcepcion.crear(
					"El identificador del país no es válido."
			);
		}
	}

	private UUID generarIdPaisUnico() {
		return UUID.randomUUID();
	}
}