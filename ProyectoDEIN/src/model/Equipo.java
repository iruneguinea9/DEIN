package model;

public class Equipo {
	private int id_equipo;
	private String equipo;
	private String iniciales;
	public Equipo(int id_equipo, String equipo, String iniciales) {
		this.id_equipo = id_equipo;
		this.equipo = equipo;
		this.iniciales = iniciales;
	}
	public int getId_equipo() {
		return id_equipo;
	}
	public String getNombre() {
		return equipo;
	}
	public String getIniciales() {
		return iniciales;
	}
	
	@Override
	public String toString() {
		return this.equipo;
	}
}
