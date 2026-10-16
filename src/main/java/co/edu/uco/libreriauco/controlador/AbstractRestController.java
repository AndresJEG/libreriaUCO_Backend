package co.edu.uco.libreriauco.controlador;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import co.edu.uco.libreriauco.controlador.respuesta.RespuestaError;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCOExcepcion;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilUUID;


@RestControllerAdvice //CONTROLADOR DE EXCEPCIONES
public abstract class AbstractRestController{
	
	
	@ExceptionHandler(LibreriaUCOExcepcion.class)
	public ResponseEntity<RespuestaError<String>> controlarLibreriaUcoException (LibreriaUCOExcepcion excepcion){
		var respuesta = RespuestaError.crear(excepcion.getMensajeUsuario());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError<String>> controlarException (Exception excepcion){
		var mensajeRespuesta = "Se ha presentado un error tratanto de llevar a cabo la operacion deseada. Por favor intente de nuevo, si persiste el problema comuniquese con el administrador, y proveale el codigo que se muestra a continuacion";		
		var respuesta = RespuestaError.crear(mensajeRespuesta);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<RespuestaError<String>> controlarException (NoResourceFoundException excepcion){
		var mensajeRespuesta = "Ojo mono porque el recurso que escribio no existe: " + excepcion.getResourcePath() + "Por el metodo: " + excepcion.getHttpMethod();		//no quemarlo.
		var respuesta = RespuestaError.crear(mensajeRespuesta);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	}
}
