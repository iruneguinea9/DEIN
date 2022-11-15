package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deporte;
import model.Evento;
import util.ConexionDB;

public class DeporteDao {
	private static ConexionDB con;
	
	public static ObservableList<Deporte> cargarDeportes() throws SQLException {
		ObservableList<Deporte> lstDeportes = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Deporte d;
		String sql = "select * from olimpiadas.Deporte";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_deporte = rs.getInt(1);
			String nombre = rs.getString(2);
			d = new Deporte(id_deporte, nombre);
			lstDeportes.add(d);
		}
		rs.close();
		ps.close();
		conn.close();

		return lstDeportes;

	}
	public static boolean aniaidirDeporte(String nombre) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		//comprobar que no estaba
		String sql = "select * from olimpiadas.Deporte where nombre = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
				return false;
		}
		pst = conn.prepareStatement(
				"insert into olimpiadas.Deporte (nombre) values (?)");
		pst.setString(1, nombre);
		pst.execute();
		pst.close();
		conn.close();

	return true;
	}
	

	public static boolean modificarDeporte(String nombre, String nombreAntes)
			throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"update olimpiadas.Deporte set nombre= ? where nombre = ?");
		pst.setString(1, nombre);
		pst.setString(2, nombreAntes);
		pst.executeUpdate();
		pst.close();
		conn.close();

		return true;
	}
	

	public static boolean eliminarDeporte(Deporte dep) throws SQLException {
		// para borrar la olimpiada, tengo que borrar los eventos que tengan esa
		// olimpiada

		con = new ConexionDB();
		Connection conn = con.getConexion();

		String sql = "select * from olimpiadas.Evento where id_deporte = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, dep.getId_deporte());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			int id_ev = rs.getInt(1);
			Evento ev = EventoDao.cualEvento(id_ev);
			EventoDao.eliminarEvento(ev);
		}
		rs.close();
		// una vez eliminado evento, y a su vez las participaciones, borro la olimpiada
		sql = "delete from olimpiadas.Deporte where id_deporte = ?";
		pst = conn.prepareStatement(sql);
		pst.setInt(1, dep.getId_deporte());
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}
	
	public static Deporte cualDeporte(int id) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Deporte where id_deporte = " + id + ";";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_deporte = rs.getInt(1);
			String nombre = rs.getString(2);
			Deporte d = new Deporte(id_deporte, nombre);
			rs.close();
			ps.close();
			conn.close();
			return d;
		}
		return null;
	}
	
}
