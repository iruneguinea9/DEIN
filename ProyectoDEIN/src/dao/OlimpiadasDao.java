package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import model.Evento;
import model.Olimpiada;

import util.ConexionDB;

public class OlimpiadasDao {
	private static ConexionDB con;

	public static ObservableList<Olimpiada> cargarOlimpiadas() throws SQLException {
		ObservableList<Olimpiada> lstOlimpiadas = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Olimpiada o;
		String sql = "select * from olimpiadas.Olimpiada";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_ol = rs.getInt(1);
			String nombre = rs.getString(2);
			String temporada = rs.getString(4);
			int anio = rs.getInt(3);
			String ciudad = rs.getString(5);
			o = new Olimpiada(id_ol, nombre, anio, temporada, ciudad);
			lstOlimpiadas.add(o);
		}
		rs.close();
		ps.close();
		conn.close();

		return lstOlimpiadas;

	}



	public static boolean aniadirOlimpiada(String nombre, int anio, String temporada, String ciudad)
			throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		// comprobar que no estaba
		String sql = "select * from olimpiadas.Olimpiada where nombre = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			return false;
		}
		sql = "insert into olimpiadas.Olimpiada (nombre,anio,temporada,ciudad) values (?,?,?,?)";
		pst = conn.prepareStatement(sql);
		pst.setString(1, nombre);
		pst.setInt(2, anio);
		pst.setString(3, temporada);
		pst.setString(4, ciudad);
		pst.execute();
		pst.close();
		conn.close();

		return true;
	}

	public static boolean modificarOlimpiada(String nombre, int anio, String temporada, String ciudad,
			String nombreAntes) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"update olimpiadas.Olimpiada set nombre= ?, anio = ? , temporada = ? , ciudad = ? where nombre = ?");
		pst.setString(1, nombre);
		pst.setInt(2, anio);
		pst.setString(3, temporada);
		pst.setString(4, ciudad);
		pst.setString(5, nombreAntes);
		pst.executeUpdate();
		pst.close();
		conn.close();

		return true;
	}

	public static Olimpiada cualOlimpiada(int id) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Olimpiada where id_olimpiada = " + id + ";";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_ol = rs.getInt(1);
			String nombre = rs.getString(2);
			String temporada = rs.getString(4);
			int anio = rs.getInt(3);
			String ciudad = rs.getString(5);
			Olimpiada o = new Olimpiada(id_ol, nombre, anio, temporada, ciudad);
			rs.close();
			ps.close();
			conn.close();
			return o;
		}
		return null;
	}

	

	public static boolean eliminarOlimpiada(Olimpiada ol) throws SQLException {
		// para borrar la olimpiada, tengo que borrar los eventos que tengan esa
		// olimpiada

		con = new ConexionDB();
		Connection conn = con.getConexion();

		String sql = "select * from olimpiadas.Evento where id_olimpiada = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, ol.getId_olimpiada());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			int id_ev = rs.getInt(1);
			Evento ev = EventoDao.cualEvento(id_ev);
			EventoDao.eliminarEvento(ev);
		}
		rs.close();
		// una vez eliminado evento, y a su vez las participaciones, borro la olimpiada
		sql = "delete from olimpiadas.Olimpiada where id_olimpiada = ?";
		pst = conn.prepareStatement(sql);
		pst.setInt(1, ol.getId_olimpiada());
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}





}
