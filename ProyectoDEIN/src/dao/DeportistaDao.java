package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deportista;
import util.ConexionDB;

public class DeportistaDao {
	private static ConexionDB con;
	
	public static ObservableList<Deportista> cargarDeportistas() throws SQLException {
		ObservableList<Deportista> lstParticipaciones = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Deportista d;
		String sql = "Select * from olimpiadas.Deportista";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_deportista = rs.getInt(1);
			String nombre = rs.getString(2);
			String sexo = rs.getString(3);
			int peso = rs.getInt(4);
			int altura = rs.getInt(5);
			InputStream foto = rs.getBinaryStream(6);
			d = new Deportista(id_deportista, nombre, sexo, peso, altura, foto);
			lstParticipaciones.add(d);
		}
		rs.close();
		ps.close();
		conn.close();

		return lstParticipaciones;
	}
	public static Boolean aniaidrDeportista(String nombre, int peso, int altura, String sexo,InputStream foto) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		//comprobar que no estaba
		String sql = "select * from olimpiadas.Deportista where nombre = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
				return false;
		}
		pst = conn.prepareStatement(
				"insert into olimpiadas.Deportista (nombre,peso,altura,sexo,foto) values (?,?,?,?,?)");
		pst.setString(1, nombre);
		pst.setInt(2, peso);
		pst.setInt(3, altura);
		pst.setString(4, sexo);
		pst.setBlob(5, foto);
		pst.execute();
		pst.close();
		conn.close();

	return true;
	}



	public static boolean modificarDeportista(String nombre, int peso, int altura, String sexo, InputStream foto, String nombreAntes)
			throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"update olimpiadas.Deportista set nombre= ?, peso = ? , altura = ? , sexo = ? ,foto = ? where nombre = ?");
		pst.setString(1, nombre);
		pst.setInt(2, peso);
		pst.setInt(3, altura);
		pst.setString(4, sexo);
		pst.setBlob(5, foto);
		pst.setString(6, nombreAntes);
		pst.executeUpdate();
		pst.close();
		conn.close();

		return true;
	}

	public static boolean eliminarDeportista(Deportista dep) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		// para borrar el equipo tengo que borrar las participaciones de ese equipo
		ParticipacionDao.eliminarParticipacion("id_deportista = " + dep.getId_deportista());
		// borrar equipo
		String sql = "delete from olimpiadas.Deportista where id_deportista = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, dep.getId_deportista());
		pst.execute();
		pst.close();
		conn.close();

		return true;
	}
	public static Deportista cualDeportista(int id) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Deportista where id_deportista = " + id + ";";
		PreparedStatement ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_deportista = rs.getInt(1);
			String nombre = rs.getString(2);
			String sexo = rs.getString(3);
			int peso = rs.getInt(4);
			int altura = rs.getInt(5);
			InputStream foto = rs.getBinaryStream(6);
			Deportista d = new Deportista(id_deportista, nombre, sexo, peso, altura, foto);
			rs.close();
			ps.close();
			conn.close();
			return d;
		}
		return null;
	}

	public static Deportista cualDeportista(String nombredep) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		String sql = "select * from olimpiadas.Deportista where nombre = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, nombredep);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id_deportista = rs.getInt(1);
			String nombre = rs.getString(2);
			String sexo = rs.getString(3);
			int peso = rs.getInt(4);
			int altura = rs.getInt(5);
			InputStream foto = rs.getBinaryStream(6);
			Deportista d = new Deportista(id_deportista, nombre, sexo, peso, altura, foto);
			rs.close();
			ps.close();
			conn.close();
			return d;
		}
		return null;
	}
}
