package co.edu.uco.libreriauco.dao.factoria;

import java.sql.Connection;

import co.edu.uco.libreriauco.dao.datos.entidad.DepartamentoDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.PaisDAO;

public abstract class DAOFactory {
	
	private Connection conexion;
	
	protected DAOFactory() {
		abrirConexion();
	}

	protected Connection getConexion() {
		return conexion;
	}

	protected void setConexion(Connection conexion) {
		//asegurar que la conexion este abierta y sea valida?
		this.conexion = conexion;
	}
	
	protected abstract void abrirConexion();//DEPENDE MUCHISIMO DEL MOTOR, NO TODOS LOS MOTORES FUNCIONAN IGUAL
		//garantizar que la conexion se obtenga de forma satisfactoria?
	
	public void cerrarConexion() {
		//como se cierra la conexion de forma segura?
	}
	
	public void iniciarTransacción() {
		//tarea: como se inicia una transacción de ofrma segura?
	}
	
	public void confirmarTransacción() {
		//tarea: como se confirma una transacción de ofrma segura?
	}
	
	public void cancelarTransacción() {
		//tarea: como se cancela una transacción de ofrma segura?
	}
	
	public abstract PaisDAO obtenerPaisDAO();
	
	public abstract DepartamentoDAO obtenerDepartamentoDAO();

}
