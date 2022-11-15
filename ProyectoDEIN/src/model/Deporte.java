package model;

public class Deporte {
	private int id_deporte;
	private String nombre;
	
	public Deporte(int id_deporte, String nombre) {
		this.id_deporte = id_deporte;
		this.nombre = nombre;
	}

	public int getId_deporte() {
		return id_deporte;
	}

	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
