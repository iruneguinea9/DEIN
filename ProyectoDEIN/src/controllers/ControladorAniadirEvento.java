package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.DeporteDao;
import dao.EventoDao;
import dao.OlimpiadasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import model.Deporte;
import model.Evento;
import model.Olimpiada;
import util.Propiedades;
import util.Utilidades;

public class ControladorAniadirEvento implements Initializable {

	@FXML
	private Button anidirBtn;

	@FXML
	private Button cancelarBtn;
	private ResourceBundle bundle;
	@FXML
	private ComboBox<Deporte> cmboDeporte;

	@FXML
	private ComboBox<Evento> cmboEventos;

	@FXML
	private ComboBox<Olimpiada> cmboOimpiada;

	@FXML
	private TextField nombreTxt;
	@FXML
	private HBox boxVisible;

	@FXML
	private HBox boxocul1;

	@FXML
	private HBox boxocul2;

	@FXML
	private HBox boxocul3;
	private Stage stage;

	@FXML
	void aniadir(ActionEvent event) {
		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			try {
				stage = (Stage) this.cancelarBtn.getScene().getWindow();
				if (stage.getTitle().equals(bundle.getString("aniaEv"))) {
					// Añadir una olimpiada
					Boolean b = EventoDao.aniadirEvento(nombreTxt.getText(),
							cmboOimpiada.getSelectionModel().getSelectedItem().getId_olimpiada(),
							cmboDeporte.getSelectionModel().getSelectedItem().getId_deporte());
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err12"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info6"));
						stage.close();
					}
				} else {
					if (anidirBtn.getText().equals(bundle.getString("modificar"))) {
						// Modificar una olimpiada
						Boolean b = EventoDao.modificarEvento(nombreTxt.getText(),
								cmboOimpiada.getSelectionModel().getSelectedItem().getId_olimpiada(),
								cmboDeporte.getSelectionModel().getSelectedItem().getId_deporte(),
								cmboEventos.getSelectionModel().getSelectedItem().getNombre());
						if (!b)
							Utilidades.mostrarAlertInfo(stage, bundle.getString("err8"));
						else {
							stage = (Stage) this.cancelarBtn.getScene().getWindow();
							Utilidades.mostrarAlertInfo(stage,bundle.getString("info7"));
							stage.close();
						}
					}
					else {
						//Esta eliminando
						Boolean b = EventoDao.eliminarEvento(cmboEventos.getSelectionModel().getSelectedItem());
						if(!b) {
							Utilidades.mostrarAlertInfo(stage, bundle.getString("err3"));
						}
						else {
							Utilidades.mostrarAlertInfo(stage, bundle.getString("info8"));
							stage.close();
						}
					}	
				}
			} catch (NumberFormatException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err9"));
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err8"));
			}
		} else {
			// tiene algun error
			Utilidades.mostrarAlertInfo(stage, str);
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		stage = (Stage) this.cancelarBtn.getScene().getWindow();
		stage.close();
	}

	@FXML
	void llenar(ActionEvent event) {
		nombreTxt.setText(cmboEventos.getSelectionModel().getSelectedItem().getNombre());
		try {
			cmboOimpiada.getSelectionModel().select(
					OlimpiadasDao.cualOlimpiada(cmboEventos.getSelectionModel().getSelectedItem().getOlimpiada().getId_olimpiada()));
			cmboDeporte.getSelectionModel().select(
					DeporteDao.cualDeporte(cmboEventos.getSelectionModel().getSelectedItem().getOlimpiada().getId_olimpiada()));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err10"));
		}
	}

	private String comprobar() {
		String str = "Error ";
		if (nombreTxt.getText().equals(""))
			str += bundle.getString("check5");
		if (nombreTxt.getText().length()>150)
			str +=bundle.getString("err19");
		if (cmboDeporte.getSelectionModel().getSelectedItem() == null)
			str += bundle.getString("check8");
		if (cmboOimpiada.getSelectionModel().getSelectedItem() == null)
			str += bundle.getString("check9");

		return str;
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			cmboEventos.setItems(EventoDao.cargarEventos());
			cmboDeporte.setItems(DeporteDao.cargarDeportes());
			cmboOimpiada.setItems(OlimpiadasDao.cargarOlimpiadas());
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err10"));
		}

	}

	public void modificar() {
		boxVisible.setVisible(true);
		anidirBtn.setText(bundle.getString("modificar"));
		nombreTxt.setDisable(true);

	}

	public void eliminar() {
		boxVisible.setVisible(true);
		anidirBtn.setText(bundle.getString("eliminar"));
		boxocul1.setVisible(false);
		boxocul2.setVisible(false);
		boxocul3.setVisible(false);
	}
}
