package model;

public class Persona {
	private String nombre;
	private String apellidos;
	private int edad;
	
	public Persona (String nombre, String apellidos, int edad) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}
	
	// Getters
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public int getEdad() {
		return edad;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public boolean equals(Persona p1) {
		if(!p1.nombre.equals(this.nombre))
			return false;
		if(!p1.apellidos.equals(this.apellidos))
			return false;
		if(p1.edad != this.edad)
			return false;

		return true;
	}
}
