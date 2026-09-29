package co.edu.uco.libreriauco;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.uco.libreriauco.dao.factoria.DAOFactory;
import co.edu.uco.libreriauco.entidad.PaisEntidad;

@Component
public class PruebaPais implements CommandLineRunner {

	private final DAOFactory fabrica;

	public PruebaPais(DAOFactory fabrica) {
		this.fabrica = fabrica;
	}

	@Override
	public void run(String... args) {
		try {
			// 1. Iniciar transacción desde la fábrica
			fabrica.iniciarTransacción();

			// 2. Obtener el DAO a través de la fábrica
			final var paisDAO = fabrica.obtenerPaisDAO();

			// 3. Crear el objeto e insertarlo
			final var pais = new PaisEntidad();
			pais.setId(UUID.randomUUID());
			pais.setNombre("Colombia");
			paisDAO.crear(pais);

			// 4. Consultar los registros existentes
			paisDAO.consultarTodos().forEach(p -> 
				System.out.println("PAIS REGISTRADO: " + p.getId() + " - " + p.getNombre())
			);

			// 5. Confirmar transacción
			fabrica.confirmarTransacción();

		} catch (Exception e) {
			// Si algo falla, cancelamos la transacción
			fabrica.cancelarTransacción();
			System.err.println("Error durante la prueba de inserción/consulta: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// 6. Cerrar la conexión únicamente mediante la fábrica
			fabrica.cerrarConexion();
		}
	}
}