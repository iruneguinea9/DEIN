package model;

import java.io.InputStream;

import javafx.scene.control.CheckBox;

public class Producto {
	private String codigo; //PK
	private String nombre;
	private Float precio;
	private int disponible;
	private InputStream imagen;
	public Producto(String codigo, String nombre, Float precio, int disponible, InputStream imagen) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.disponible=disponible;
		this.imagen = imagen;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public Float getPrecio() {
		return precio;
	}
	public int getDisponible() {
		return disponible;
	}
	public InputStream getImagen() {
		return imagen;
	}
	

}
