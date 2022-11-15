package model;

public class Olimpiada {
	private int id_olimpiada;
	private String nombre;
	private int anio;
	private String temporada;
	private String ciudad;

	public Olimpiada(int id, String nombre, int anio, String temporada, String ciudad) {

		this.id_olimpiada = id;
		this.nombre = nombre;
		this.anio = anio;
		this.temporada = temporada;
		this.ciudad = ciudad;
	}

	public int getId_olimpiada() {
		return id_olimpiada;
	}

	public void setId_olimpiada(int id_olimpiada) {
		this.id_olimpiada = id_olimpiada;
	}

	public String getNombre() {
		return nombre;
	}

	public int getAnio() {
		return anio;
	}

	public String getTemporada() {
		return temporada;
	}

	public String getCiudad() {
		return ciudad;
	}
	@Override
	public String toString() {
		return nombre;
	}

}
