package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladorVentanaModalImagen {

    @FXML
    private ImageView imagen;
    public void cargarImagen(Image im) {
    	this.imagen.setImage(im);
    }
}
