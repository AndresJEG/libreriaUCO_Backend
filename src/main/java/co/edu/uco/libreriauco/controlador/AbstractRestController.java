package co.edu.uco.libreriauco.controlador;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.edu.uco.libreriauco.controlador.respuesta.RespuestaError;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCOExcepcion;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilUUID;


@RestControllerAdvice
public abstract class AbstractRestController{
	
	
	@ExceptionHandler(LibreriaUCOExcepcion.class)
	public ResponseEntity<RespuestaError<String>> controlarLibreriaUcoException (LibreriaUCOExcepcion excepcion){
		var respuesta = RespuestaError.crear(excepcion.getMensajeUsuario());
		//logger.printException(excepcion); es para la traza de la excepcion
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError<String>> controlarException (Exception excepcion){
		var mensajeRespuesta = "Se ha presentado un eror tratanto de llevar a cabo la operacion deseada. Por favor intente de nuevo, si persiste el problema comuniquese con el administrador, y proveale el codigo que se muestra a continuacion";//EL CONCAT SE DEBE DE REALIZAR EN EL UTILTEXTO
		var respuesta = RespuestaError.crear(mensajeRespuesta);
		//logger.printException(excepcion); es para la traza de la excepcion
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	}
}
