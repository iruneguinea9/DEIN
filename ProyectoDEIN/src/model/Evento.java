package model;

public class Evento {
	private int id_evento;
	private String evento;
	private Olimpiada olimpiada;
	private Deporte deporte;
	public Evento(int id_evento, String evento, Olimpiada olimpiada, Deporte deporte) {
		this.id_evento = id_evento;
		this.evento = evento;
		this.olimpiada = olimpiada;
		this.deporte = deporte;
	}
	public int getId_evento() {
		return id_evento;
	}
	public String getNombre() {
		return evento;
	}
	public Olimpiada getOlimpiada() {
		return olimpiada;
	}
	public Deporte getDeporte() {
		return deporte;
	}
	@Override
	public String toString() {

		return evento +  " "+this.olimpiada.getNombre();
	}
}
