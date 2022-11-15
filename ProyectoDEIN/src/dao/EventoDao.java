package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deporte;
import model.Evento;
import model.Olimpiada;
import util.ConexionDB;

public class EventoDao {
	private static ConexionDB con;

	public static ObservableList<Evento> cargarEventos() throws SQLException {
		ObservableList<Evento> lsteventos = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Evento e;
		String sql = "select * from olimpiadas.Evento";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_evento = rs.getInt(1);
			String nombre = rs.getString(2);
			int id_olimpiada = rs.getInt(3);
			int id_deporte = rs.getInt(4);
			Olimpiada ol = OlimpiadasDao.cualOlimpiada(id_olimpiada);
			Deporte dep = DeporteDao.cualDeporte(id_deporte);
			e = new Evento(id_evento, nombre, ol, dep);
			lsteventos.add(e);
		}
		rs.close();
		ps.close();
		conn.close();

		return lsteventos;

	}

	public static Boolean aniadirEvento(String nombre, int id_olimpiada, int id_deporte) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		// comprobar que no estaba (no hace falta comprobar deporte, ya que va en el propio nombre del evento)
		String sql = "select * from olimpiadas.Evento where nombre = ? and id_olimpiada = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, nombre);
		pst.setInt(2, id_olimpiada);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			return false;
		}
		pst = conn.prepareStatement("insert into olimpiadas.Evento (nombre,id_olimpiada,id_deporte) values (?,?,?)");
		pst.setString(1, nombre);
		pst.setInt(2, id_olimpiada);
		pst.setInt(3, id_deporte);
		pst.execute();
		pst.close();
		conn.close();

		return true;
	}

	public static boolean modificarEvento(String nombre, int id_olimpiada, int id_deporte, String nombreAntes)
			throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"update olimpiadas.Evento set nombre= ?, id_olimpiada = ?,id_deporte= ?  where nombre = ?");
		pst.setString(1, nombre);
		pst.setInt(2, id_olimpiada);
		pst.setInt(3, id_deporte);
		pst.setString(4, nombreAntes);
		pst.executeUpdate();
		pst.close();
		conn.close();

		return true;
	}
	public static boolean eliminarEvento(Evento ev) throws SQLException {
		// para borrar el evento, tengo que borrar las participaciones que tengan ese
		// evento
		ParticipacionDao.eliminarParticipacion("id_evento = " + ev.getId_evento());
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "delete from olimpiadas.Evento where id_evento = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, ev.getId_evento());
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}

	public static Evento cualEvento(int id) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Evento where id_evento = " + id + ";";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_evento = rs.getInt(1);
			String nombre = rs.getString(2);
			int id_olimpiada = rs.getInt(3);
			int id_deporte = rs.getInt(4);
			Olimpiada ol = OlimpiadasDao.cualOlimpiada(id_olimpiada);
			Deporte dep = DeporteDao.cualDeporte(id_deporte);
			Evento e = new Evento(id_evento, nombre, ol, dep);
			rs.close();
			ps.close();
			conn.close();
			return e;
		}
		return null;
	}

	public static Evento cualEvento(String nombreev) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Evento where nombre = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, nombreev);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_evento = rs.getInt(1);
			String nombre = rs.getString(2);
			int id_olimpiada = rs.getInt(3);
			int id_deporte = rs.getInt(4);
			Olimpiada ol = OlimpiadasDao.cualOlimpiada(id_olimpiada);
			Deporte dep = DeporteDao.cualDeporte(id_deporte);
			Evento e = new Evento(id_evento, nombre, ol, dep);
			rs.close();
			ps.close();
			conn.close();
			return e;
		}
		return null;
	}
}
