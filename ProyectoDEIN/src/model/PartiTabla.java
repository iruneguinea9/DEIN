package model;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PartiTabla {
	private ImageView foto;
	private String nombre;
	private String sexo;
	private int peso;
	private int altura;
	private int edad;
	private String equipo;
	private String evento;
	private String medalla;

	public PartiTabla(Blob blob, String nombre, String sexo, int peso, int altura, int edad, String equipo,
			String evento, String medalla) {
		if (blob == null) {
			Image foto = new Image(getClass().getResourceAsStream("/images/avatar.jpg"),100,100, false, false);
			this.foto = new ImageView(foto);
		} else {

			try {
				InputStream is = blob.getBinaryStream();
				this.foto = new ImageView(new Image(is,100,100, false, false));
				is.close();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.nombre = nombre;
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;
		this.edad = edad;
		this.equipo = equipo;
		this.evento = evento;
		this.medalla = medalla;
	}

	public ImageView getFoto() {
		return foto;
	}

	public String getNombre() {
		return nombre;
	}

	public String getSexo() {
		return sexo;
	}

	public int getPeso() {
		return peso;
	}

	public int getAltura() {
		return altura;
	}

	public int getEdad() {
		return edad;
	}

	public String getEquipo() {
		return equipo;
	}

	public String getEvento() {
		return evento;
	}

	public String getMedalla() {
		return medalla;
	}

}
