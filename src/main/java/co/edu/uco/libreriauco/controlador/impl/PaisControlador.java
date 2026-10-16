package co.edu.uco.libreriauco.controlador.impl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.libreriauco.controlador.AbstractRestController;


@RestController //anotacion para controller y es para la web
@RequestMapping("/api/v1/paises") //es para ingresar al controller completo de paises
public class PaisControlador extends AbstractRestController{
	
	
	@GetMapping
	public String consultarTodos() {
		return "Ahora si, metodo GET exitoso";
	}
	
	@PutMapping
	public String modificarInformacionPaisExistente() {
		return "Aca es para modificar  PUT";
	}
	
	@DeleteMapping
	public String darBajaInformacionPaisExistente() {
		return "aca se elimina DELETE";
	}
	
	@PostMapping
	public String registrarInformacionNuevoPais() {
		return "aca se elimina POST";
	}

}
