	package co.edu.uco.libreriauco.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.libreriauco.dao.factoria.DAOFactory;
import co.edu.uco.libreriauco.dominio.PaisDominio;
import co.edu.uco.libreriauco.dto.PaisDTO;
import co.edu.uco.libreriauco.negocio.fachada.PaisFachada;
import co.edu.uco.libreriauco.negocio.fachada.assembler.impl.PaisDTOAssembler;
import co.edu.uco.libreriauco.negocio.negocio.PaisNegocio;
import co.edu.uco.libreriauco.negocio.negocio.impl.PaisNegocioImpl;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCOExcepcion;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCONegocioExcepcion;

public class PaisFachadaImpl implements PaisFachada{
	
	private DAOFactory daoFactory;
	private PaisNegocio paisNegocio;
	
	public PaisFachadaImpl() {
		daoFactory = DAOFactory.obtenerFactoria();
		paisNegocio = new PaisNegocioImpl(daoFactory);
	}

	@Override
	public void registrarInformacionNuevoPais(PaisDTO datos) {
		daoFactory.iniciarTransacción();
		
		try {
			var paisDominio = PaisDTOAssembler.getInstance().convertirADominio(datos);
			paisNegocio.registrarInformacionNuevoPais(paisDominio);
			
			daoFactory.confirmarTransacción();
			
		} catch (LibreriaUCOExcepcion excepcion) {
			daoFactory.cancelarTransacción();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransacción();
			
			var mensajeUsuario = "Se ha presentado un problema inesperado tratando de registrar la información del nuevo pais deseado. Por favor intyente de nuevo, y si el problema persiste contacte al administrador de la aplicacion";
			throw LibreriaUCONegocioExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		} finally {
			daoFactory.cerrarConexion();
		}
		
	}

	@Override
	public void modificarInformacionPaisExistente(UUID id, PaisDTO datos) {
		daoFactory.iniciarTransacción();
				
			try {
				var paisDominio = PaisDTOAssembler.getInstance().convertirADominio(datos);
				paisNegocio.modificarInformacionPaisExistente(paisDominio.getId(), paisDominio);;
				
				daoFactory.confirmarTransacción();
				
			} catch (LibreriaUCOExcepcion excepcion) {
				daoFactory.cancelarTransacción();
				throw excepcion;
			} catch (Exception excepcion) {
				daoFactory.cancelarTransacción();
				
				var mensajeUsuario = "Se ha presentado un problema inesperado tratando de modificar la información del pais deseado. Por favor intyente de nuevo, y si el problema persiste contacte al administrador de la aplicacion";
				throw LibreriaUCONegocioExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
			} finally {
				daoFactory.cerrarConexion();
			}
		
	}

	@Override
	public void darBajaInformacionPaisExistente(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PaisDTO> consultarPorFiltro(PaisDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaisDTO> consultarTodos() {
				
			try {
				
				var listaPaisesDominio = paisNegocio.consultarTodos();
				return PaisDTOAssembler.getInstance().convertirADTO(listaPaisesDominio);
				
			} catch (LibreriaUCOExcepcion excepcion) {
				throw excepcion;
			} catch (Exception excepcion) {
				
				var mensajeUsuario = "Se ha presentado un problema inesperado tratando consultar la información de todos los paises. Por favor intyente de nuevo, y si el problema persiste contacte al administrador de la aplicacion";
				throw LibreriaUCONegocioExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
			} finally {
				daoFactory.cerrarConexion();
			}
	}

	@Override
	public PaisDTO consultarPorID(UUID id) {
		return null;
	}

}
