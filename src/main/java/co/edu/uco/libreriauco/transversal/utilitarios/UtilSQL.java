package co.edu.uco.libreriauco.transversal.utilitarios;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.uco.libreriauco.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCOTransversalExcepcion;

public class UtilSQL {
	
	private UtilSQL() {
		
	}
	
	public static boolean conexionEstaAbierta(Connection conexion) {
		try {
			return (!conexionEstaVacia(conexion) && !conexion.isClosed());
		} catch (SQLException excepcion) {
			
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_PROBLEMA_VALIDANDO_SI_CONEXION_SQL_ESTA_ABIERTA;
	
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
			
		} catch (Exception excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_VALIDANDO_SI_CONEXION_SQL_ESTA_ABIERTA;
			
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}
	
	
	public static void asegurarConexionAbierta (Connection conexion) {
		if(!conexionEstaAbierta(conexion)) {
			var mensajeUsuario = "Mensaje que indique en términos de usuario que no es posible...";
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario);
		}
	}
	
	public static void iniciarTransaccion(Connection conexion) {
		if (transaccionEstaIniciada(conexion)) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_NO_ES_POSIBLE_INICIAR_TRANSACCION_SQL;
			
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario);
		}
		
		//aqui va la tarea que puso el profe para iniciar
	}
	
	public static void confirmarTransaccion(Connection conexion) {
		if (!transaccionEstaIniciada(conexion)) {
			var mensajeUsuario = "aqui va el mensaje hecho en el catalogo de mensajes especial para la no confirmación de la transacción"; 
					
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario);
		}
		
		//aqui va la tarea que puso el profe para confirmar
	}
	
	public static void cancelarTransaccion(Connection conexion) {
		if (!transaccionEstaIniciada(conexion)) {
			var mensajeUsuario = "aqui va el mensaje hecho en el catalogo de mensajes especial para la no confirmación de la transacción"; 
					
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario);
		}
		
		//aqui va la tarea que puso el profe para cancelar
	}
	
	public static void cerrarConexion (Connection conexion) {
		if(!conexionEstaAbierta(conexion)) {
			var mensajeUsuario = "mensaje en el catalogo de que no se puede cerrar algo que no esta abiero";
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario);
		}
		
		//tarea del profe
	}
	
	public static boolean transaccionEstaIniciada(Connection conexion) {
		try {
			return conexionEstaAbierta(conexion) && !conexion.getAutoCommit();
		} catch (SQLException excepcion) {
			
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_PROBLEMA_VALIDANDO_SI_TRANSACCION_SQL_ESTA_INICIADA;
	
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
			
		} catch (Exception excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_VALIDANDO_SI_TRANSACCION_SQL_ESTA_INICIADA;
			
			throw LibreriaUCOTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}
	
	public static boolean conexionEstaVacia (Connection conexion) {
		return UtilObjeto.esNulo(conexion);
	}

}
