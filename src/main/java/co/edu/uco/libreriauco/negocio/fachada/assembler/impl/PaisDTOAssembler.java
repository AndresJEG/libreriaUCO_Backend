package co.edu.uco.libreriauco.negocio.fachada.assembler.impl;

import java.util.List;

import co.edu.uco.libreriauco.dominio.PaisDominio;
import co.edu.uco.libreriauco.dto.PaisDTO;
import co.edu.uco.libreriauco.entidad.PaisEntidad;
import co.edu.uco.libreriauco.negocio.fachada.assembler.DTOAssembler;
import co.edu.uco.libreriauco.negocio.negocio.assembler.EntidadAssembler;
import co.edu.uco.libreriauco.negocio.negocio.assembler.impl.PaisEntidadAssembler;

public class PaisDTOAssembler implements DTOAssembler<PaisDominio, PaisDTO>{
	
	private static final DTOAssembler<PaisDominio, PaisDTO> instancia = new PaisDTOAssembler();

	
	public PaisDTOAssembler() {

	}
	
	public static DTOAssembler<PaisDominio, PaisDTO> getInstance(){
		return instancia;
	}

	@Override
	public PaisDTO convertirADTO(PaisDominio dominio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaisDominio convertirADominio(PaisDTO DTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaisDTO> convertirADTO(List<PaisDominio> listaDominios) {
		return listaDominios.stream().map(this::convertirADTO).toList();
	}

}
