package model;

public class Participacion {
	private Deportista deportista;
	private Evento evento;
	private Equipo equipo;
	private int edad;
	private String medalla;
	public Participacion(Deportista deportista, Evento evento, Equipo equipo, int edad, String medalla) {

		this.deportista = deportista;
		this.evento = evento;
		this.equipo = equipo;
		this.edad = edad;
		this.medalla = medalla;
	}
	public Deportista getDeportista() {
		return deportista;
	}
	public Evento getEvento() {
		return evento;
	}
	public Equipo getEquipo() {
		return equipo;
	}
	public int getEdad() {
		return edad;
	}
	public String getMedalla() {
		return medalla;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	
}
