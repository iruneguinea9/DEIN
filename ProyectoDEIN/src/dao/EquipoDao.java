package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Equipo;
import util.ConexionDB;

public class EquipoDao {
	private static ConexionDB con;
	
	public static ObservableList<Equipo> cargarEquipos() throws SQLException {
		ObservableList<Equipo> lstequipos = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Equipo e;
		String sql = "select * from olimpiadas.Equipo";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_equipo = rs.getInt(1);
			String nombre = rs.getString(2);
			String noc = rs.getString(3);
			e = new Equipo(id_equipo, nombre, noc);
			lstequipos.add(e);
		}
		rs.close();
		ps.close();
		conn.close();

		return lstequipos;

	}
	public static Boolean aniadirEquipo(String nombre, String noc) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		//comprobar que no estaba
		String sql = "select * from olimpiadas.Equipo where nombre = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
				return false;
		}
		pst = conn.prepareStatement(
				"insert into olimpiadas.Equipo (nombre,iniciales) values (?,?)");
		pst.setString(1, nombre);
		pst.setString(2, noc);
		pst.execute();
		pst.close();
		conn.close();

	return true;
	}
	public static boolean modificarEquipo(String nombre, String noc,String nombreAntes) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"update olimpiadas.Equipo set nombre= ?, iniciales = ?  where nombre = ?");
		pst.setString(1, nombre);
		pst.setString(2, noc);
		pst.setString(3, nombreAntes);
		pst.executeUpdate();
		pst.close();
		conn.close();

	return true;
}
	public static Equipo cualEquipo(int id) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Equipo where id_equipo = " + id + ";";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_equipo = rs.getInt(1);
			String nombre = rs.getString(2);
			String noc = rs.getString(3);
			Equipo e = new Equipo(id_equipo, nombre, noc);
			rs.close();
			ps.close();
			conn.close();
			return e;
		}
		return null;
	}

	public static Equipo cualEquipo(String nombreeq) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Equipo where nombre = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, nombreeq);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_equipo = rs.getInt(1);
			String nombre = rs.getString(2);
			String noc = rs.getString(3);
			Equipo e = new Equipo(id_equipo, nombre, noc);
			rs.close();
			ps.close();
			conn.close();
			return e;
		}
		return null;
	}
	

	public static boolean eliminarEquipo(Equipo eq) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		// para borrar el equipo tengo que borrar las participaciones de ese equipo
		ParticipacionDao.eliminarParticipacion("id_equipo = " + eq.getId_equipo());
		// borrar equipo
		String sql = "delete from olimpiadas.Equipo where id_equipo = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, eq.getId_equipo());
		pst.execute();
		pst.close();
		conn.close();

		return true;
	}

}
