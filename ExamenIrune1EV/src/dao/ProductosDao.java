package dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Producto;
import util.ConexionDB;

public class ProductosDao {
	private static ConexionDB con;
	
	// ------------------ Cargar Tabla --------------------
	public static ObservableList<Producto> cargarTabla() throws SQLException {
		ObservableList<Producto> lstPartiTabla = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Producto pt;
		String sql = "SELECT * FROM examen1.productos";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			pt = new Producto(rs.getString(1),rs.getString(2),rs.getFloat(3),rs.getInt(4),rs.getBinaryStream(5));
			lstPartiTabla.add(pt);
		}
		return lstPartiTabla;
	}

	public static Boolean aniadirProducto(String codigo, String nombre, float precio, int disponible,
			InputStream foto) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		//comprobar que no estaba
		String sql = "select * from examen1.productos where codigo = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, codigo);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
				return false;
		}
		pst = conn.prepareStatement(
				"insert into examen1.productos values (?,?,?,?,?)");
		pst.setString(1, codigo);
		pst.setString(2, nombre);
		pst.setFloat(3, precio);
		pst.setInt(4, disponible);
		pst.setBlob(5, foto);
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}

	public static Boolean modificarProducto(String codigo, String nombre, float precio, int disponible,
			InputStream foto) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"update examen1.productos set nombre= ?, precio = ? , disponible = ? , imagen = ? where codigo = ?");
		pst.setString(1, nombre);
		pst.setFloat(2, precio);
		pst.setInt(3, disponible);
		pst.setBlob(4, foto);
		pst.setString(5, codigo);
		pst.executeUpdate();
		pst.close();
		conn.close();
		
		return true;
	}

	public static Boolean eliminarProducto(String codigo) throws SQLException {
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement(
				"delete from examen1.productos where codigo = ?");
		pst.setString(1, codigo);
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}
	
	public static InputStream dameLaFoto(String codigo) throws SQLException {
		InputStream foto = null;
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement("select imagen from examen1.productos where codigo = ? ");
		pst.setString(1, codigo);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			 foto = rs.getBinaryStream(1);
		}
		rs.close();

		return foto;
	}
}
