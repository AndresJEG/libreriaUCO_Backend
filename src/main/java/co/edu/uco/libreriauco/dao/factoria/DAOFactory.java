package co.edu.uco.libreriauco.dao.factoria;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.uco.libreriauco.dao.datos.entidad.DepartamentoDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.PaisDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.sqlserver.DepartamentoSqlServerDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.sqlserver.PaisSqlServerDAO;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilSQL;

public abstract class DAOFactory {
	
	private Connection conexion;
	
	protected DAOFactory() {
		abrirConexion();
	}

	protected Connection getConexion() {
		return conexion;
	}

	protected void setConexion(Connection conexion) {
		try {
			if (conexion == null || conexion.isClosed()) {
				throw new RuntimeException("La conexión no puede ser nula ni estar cerrada.");
			}
			this.conexion = conexion;
		} catch (SQLException e) {
			throw new RuntimeException("Error al validar el estado de la conexión.", e);
		}
	}
	
	protected abstract void abrirConexion(); 
	
	public void cerrarConexion() {
		UtilSQL.cerrarConexion(conexion);
	}
	
	public void iniciarTransacción() {
		UtilSQL.iniciarTransaccion(conexion);
	}
	
	public void confirmarTransacción() {
		UtilSQL.confirmarTransaccion(conexion);
	}
	
	public void cancelarTransacción() {
		UtilSQL.confirmarTransaccion(conexion);
	}
	
	public PaisDAO obtenerPaisDAO() {
		return new PaisSqlServerDAO(getConexion());
	};
	
	public DepartamentoDAO obtenerDepartamentoDAO() {
		return new DepartamentoSqlServerDAO(getConexion());
	};

}
