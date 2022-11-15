package model;

import java.io.InputStream;

public class Deportista {
	private int id_deportista;
	private String nombre;
	private String sexo;
	private int peso;
	private int altura;
	private InputStream foto;
	
	public Deportista(int id_deportista, String nombre, String sexo, int peso, int altura, InputStream foto) {
		this.id_deportista = id_deportista;
		this.nombre = nombre;
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;
		this.foto = foto;
	}

	public int getId_deportista() {
		return id_deportista;
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

	public InputStream getFoto() {
		return foto;
	}
	@Override
	public String toString() {
		return this.nombre;
	}
}
