package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.PartiTabla;
import model.Participacion;
import util.ConexionDB;

public class ParticipacionDao {
	private static ConexionDB con;

	public static ObservableList<Participacion> cargarParticipaciones() throws SQLException {
		ObservableList<Participacion> lstParticipaciones = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Participacion p;
		String sql = "Select * from olimpiadas.Participacion";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_deportista = rs.getInt(1);
			int id_evento = rs.getInt(2);
			int id_equipo = rs.getInt(3);
			int edad = rs.getInt(4);
			String medalla = rs.getString(5);
			Deportista depta = DeportistaDao.cualDeportista(id_deportista);
			Evento ev = EventoDao.cualEvento(id_evento);
			Equipo eq = EquipoDao.cualEquipo(id_equipo);
			p = new Participacion(depta, ev, eq, edad, medalla);
			lstParticipaciones.add(p);
		}
		rs.close();
		ps.close();
		conn.close();

		return lstParticipaciones;
	}

	public static Boolean aniadirParticipacion(int id_deportista, int id_evento, int id_equipo, int edad,
			String medalla) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		// comprobar que no estaba
		String sql = "select * from olimpiadas.Participacion where id_deportista = ? and id_evento = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, id_deportista);
		pst.setInt(2, id_evento);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			return false;
		}
		pst = conn.prepareStatement(
				"insert into olimpiadas.Participacion (id_deportista,id_evento,id_equipo,edad,medalla) values (?,?,?,?,?)");
		pst.setInt(1, id_deportista);
		pst.setInt(2, id_evento);
		pst.setInt(3, id_equipo);
		pst.setInt(4, edad);
		pst.setString(5, medalla);
		pst.execute();

		pst.close();
		conn.close();

		return true;
	}

	public static boolean modificarParticipacion(int id_deportista, int id_evento, int id_equipo, int edad,
			String medalla, int id_dep_ant, int id_ev_ant) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"update olimpiadas.Participacion set id_deportista= ?, id_evento = ?,id_equipo = ? ,edad=?,medalla=? where id_deportista = ? and id_evento= ?");
		pst.setInt(1, id_deportista);
		pst.setInt(2, id_evento);
		pst.setInt(3, id_equipo);
		pst.setInt(4, edad);
		pst.setString(5, medalla);
		pst.setInt(6, id_dep_ant);
		pst.setInt(7, id_ev_ant);
		pst.executeUpdate();
		pst.close();
		conn.close();

		return true;
	}
	public static Participacion cualParticipacion(Deportista dep,Evento ev) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Participacion where id_deportista = ? and id_evento = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, dep.getId_deportista());
		ps.setInt(2, ev.getId_evento());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Equipo eq = EquipoDao.cualEquipo(rs.getInt(3));
			int edad = rs.getInt(4);
			String medalla = rs.getString(5);
			Participacion p = new Participacion(dep,ev,eq,edad,medalla);
			rs.close();
			ps.close();
			conn.close();
			return p;
		}
		return null;
	}

	

	public static boolean eliminarParticipacion(Participacion p) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "delete from olimpiadas.Participacion where id_deportista=? and id_evento = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, p.getDeportista().getId_deportista());
		pst.setInt(2, p.getEvento().getId_evento());
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}

	public static boolean eliminarParticipacion(String sql2) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "delete from olimpiadas.Participacion where " + sql2;
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}

	public static ObservableList<PartiTabla> cargarTabla(String filtro) throws SQLException {
		ObservableList<PartiTabla> lstPartiTabla = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PartiTabla pt;
		String sql = "SELECT * FROM olimpiadas.Deportista,olimpiadas.Deporte,olimpiadas.Equipo,olimpiadas.Evento,olimpiadas.Olimpiada,olimpiadas.Participacion where olimpiadas.Participacion.id_deportista = olimpiadas.Deportista.id_deportista and olimpiadas.Evento.id_evento = olimpiadas.Participacion.id_evento and olimpiadas.Equipo.id_equipo = olimpiadas.Participacion.id_equipo and olimpiadas.Evento.id_deporte = olimpiadas.Deporte.id_deporte and olimpiadas.Olimpiada.id_olimpiada = olimpiadas.Evento.id_olimpiada ";
		sql += filtro;
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			pt = new PartiTabla(rs.getBlob(6), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
					rs.getInt(24), rs.getString(10), rs.getString(13), rs.getString(25));
			lstPartiTabla.add(pt);
		}
		return lstPartiTabla;
	}

	public static String dameLaFoto(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
}
