package co.edu.uco.libreriauco.dao.datos.entidad.sqlserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.libreriauco.dao.datos.entidad.PaisDAO;
import co.edu.uco.libreriauco.dao.datos.entidad.SqlDAO;
import co.edu.uco.libreriauco.entidad.PaisEntidad;
import co.edu.uco.libreriauco.transversal.excepciones.LibreriaUCOTransversalExcepcion;
import co.edu.uco.libreriauco.transversal.utilitarios.UtilUUID;

public class PaisSqlServerDAO extends SqlDAO implements PaisDAO {

	public PaisSqlServerDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(PaisEntidad entidad) {
		final var sql = "INSERT INTO Pais (id, nombre) VALUES (?, ?)";
		try (var sentencia = getConnection().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getId().toString());
			sentencia.setString(2, entidad.getNombre());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw LibreriaUCOTransversalExcepcion.crear(
					"No fue posible registrar el país.", excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public PaisEntidad consultarPorId(UUID id) {
		final var sql = "SELECT id, nombre FROM Pais WHERE id = ?";
		try (var sentencia = getConnection().prepareStatement(sql)) {
			sentencia.setString(1, id.toString());
			try (var resultado = sentencia.executeQuery()) {
				return resultado.next() ? mapearFila(resultado) : null;
			}
		} catch (SQLException excepcion) {
			throw LibreriaUCOTransversalExcepcion.crear(
					"No fue posible consultar el país.", excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public List<PaisEntidad> consultarPorFiltro(PaisEntidad filtro) {
		final var sql = new StringBuilder("SELECT id, nombre FROM Pais WHERE 1 = 1");
		final var parametros = new ArrayList<String>();

		if (filtro.getId() != null && !filtro.getId().equals(UtilUUID.VALOR_DEFECTO)) {
			sql.append(" AND id = ?");
			parametros.add(filtro.getId().toString());
		}
		if (filtro.getNombre() != null && !filtro.getNombre().isBlank()) {
			sql.append(" AND nombre LIKE ?");
			parametros.add("%" + filtro.getNombre() + "%");
		}

		try (var sentencia = getConnection().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				sentencia.setString(i + 1, parametros.get(i));
			}
			try (var resultado = sentencia.executeQuery()) {
				final List<PaisEntidad> paises = new ArrayList<>();
				while (resultado.next()) {
					paises.add(mapearFila(resultado));
				}
				return paises;
			}
		} catch (SQLException excepcion) {
			throw LibreriaUCOTransversalExcepcion.crear(
					"No fue posible consultar los países con el filtro dado.", excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public List<PaisEntidad> consultarTodos() {
		final var sql = "SELECT id, nombre FROM Pais";
		try (var sentencia = getConnection().prepareStatement(sql);
				var resultado = sentencia.executeQuery()) {
			final List<PaisEntidad> paises = new ArrayList<>();
			while (resultado.next()) {
				paises.add(mapearFila(resultado));
			}
			return paises;
		} catch (SQLException excepcion) {
			throw LibreriaUCOTransversalExcepcion.crear(
					"No fue posible consultar los países.", excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public void actualizar(UUID id, PaisEntidad entidad) {
		final var sql = "UPDATE Pais SET nombre = ? WHERE id = ?";
		try (var sentencia = getConnection().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getNombre());
			sentencia.setString(2, id.toString());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw LibreriaUCOTransversalExcepcion.crear(
					"No fue posible actualizar el país.", excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public void eliminar(UUID id) {
		final var sql = "DELETE FROM Pais WHERE id = ?";
		try (var sentencia = getConnection().prepareStatement(sql)) {
			sentencia.setString(1, id.toString());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw LibreriaUCOTransversalExcepcion.crear(
					"No fue posible eliminar el país.", excepcion.getMessage(), excepcion);
		}
	}

	private PaisEntidad mapearFila(ResultSet resultado) throws SQLException {
		final var entidad = new PaisEntidad();
		entidad.setId(UUID.fromString(resultado.getString("id")));
		entidad.setNombre(resultado.getString("nombre"));
		return entidad;
	}
}
