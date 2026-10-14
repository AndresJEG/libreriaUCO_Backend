package co.edu.uco.libreriauco.dao.factoria;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.uco.libreriauco.dao.datos.entidad.DepartamentoDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.PaisDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.sqlserver.DepartamentoSqlServerDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.sqlserver.PaisSqlServerDAO;
import co.edu.uco.libreriauco.dao.factoria.enums.FactoriaEnum;
import co.edu.uco.libreriauco.dao.factoria.impl.SqlServerDAOFactory;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCODatosExcepcion;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilSQL;

public abstract class DAOFactory {
	
	private Connection conexion;
	private static FactoriaEnum factoria = FactoriaEnum.SQLSERVER;
	
	protected DAOFactory() {
		abrirConexion();
	}
	
	public static DAOFactory obtenerFactoria() {
		switch (factoria) {
		case SQLSERVER:
			return new SqlServerDAOFactory();
		default:
			var mensajeUsusario = "La fuente de indformacion deseada quie se solicito para llevar a cabo la operacion no esta habilidtaDA. Por favor contacte el administrador de la aplicacion";
			throw LibreriaUCODatosExcepcion.crear(mensajeUsusario);
		}
	}

	protected Connection getConexion() {
		return conexion;
	}

	protected void setConexion(Connection conexion) {
	    if (conexion == null) {
	        throw LibreriaUCODatosExcepcion.crear(
	            "Se ha presentado un problema al intentar asignar la conexión a la base de datos.",
	            "La conexión recibida en el método setConexion() es nula."
	        );
	    }

	    try {
	        if (conexion.isClosed()) {
	            throw LibreriaUCODatosExcepcion.crear(
	                "Se ha presentado un problema al intentar asignar la conexión a la base de datos.",
	                "La conexión recibida en el método setConexion() se encuentra cerrada."
	            );
	        }
	        this.conexion = conexion;
	    } catch (SQLException e) {
	        throw LibreriaUCODatosExcepcion.crear(
	            "Se ha presentado un problema inesperado al validar el estado de la conexión.",
	            "Se lanzó una SQLException al invocar el método conexion.isClosed().",
	            e
	        );
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
