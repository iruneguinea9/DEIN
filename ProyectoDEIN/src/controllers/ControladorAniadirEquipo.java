package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.EquipoDao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import model.Equipo;
import util.Propiedades;
import util.Utilidades;

public class ControladorAniadirEquipo implements Initializable {
	@FXML
	private Button aniadirBtn;
	private ResourceBundle bundle;
	@FXML
	private HBox boxVisible;

	@FXML
	private Button cancelarBtn;

	@FXML
	private ComboBox<Equipo> cmboEquipos;
	@FXML
	private GridPane ocul;
	@FXML
	private TextField nocTxt;

	@FXML
	private TextField nombreTxt;
	private Stage stage;

	@FXML
	void aniaidr(ActionEvent event) {
		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			try {
				stage = (Stage) this.cancelarBtn.getScene().getWindow();
				if (stage.getTitle().equals(bundle.getString("aniaEquip"))) {
					// Añadir una olimpiada
					Boolean b = EquipoDao.aniadirEquipo(nombreTxt.getText(), nocTxt.getText());
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err13"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info9"));
						stage.close();
					}
				} else {
					if (aniadirBtn.getText().equals(bundle.getString("modificar"))) {
						// Modificar una olimpiada
						Boolean b = EquipoDao.modificarEquipo(nombreTxt.getText(), nocTxt.getText(),
								cmboEquipos.getSelectionModel().getSelectedItem().getNombre());
						if (!b)
							Utilidades.mostrarAlertInfo(stage, bundle.getString("err8"));
						else {
							stage = (Stage) this.cancelarBtn.getScene().getWindow();
							Utilidades.mostrarAlertInfo(stage, bundle.getString("info10"));
							stage.close();
						}
					}
					else {
						// eliminar
						Boolean b = EquipoDao.eliminarEquipo(cmboEquipos.getSelectionModel().getSelectedItem());
						if(!b) {
							Utilidades.mostrarAlertInfo(stage, bundle.getString("err3"));
						}
						else {
							Utilidades.mostrarAlertInfo(stage, bundle.getString("info11"));
							stage.close();
						}
					}
				}
			} catch (NumberFormatException e) {
				Utilidades.mostrarAlertInfo(stage,bundle.getString("err9"));
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
		nombreTxt.setText(cmboEquipos.getSelectionModel().getSelectedItem().getNombre());
		nocTxt.setText(cmboEquipos.getSelectionModel().getSelectedItem().getIniciales());
	}

	private String comprobar() {
		String str = "Error ";
		if (nombreTxt.getText().equals(""))
			str += bundle.getString("check5");
		if (nocTxt.getText().equals(""))
			str += bundle.getString("check10");
		if (nombreTxt.getText().length()>50)
			str +=bundle.getString("err19");
		else {
			if (nocTxt.getText().length() > 3)
				str += bundle.getString("check11");
		}

		return str;
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			cmboEquipos.setItems(EquipoDao.cargarEquipos());
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err10"));
		}

	}

	public void modificar() {
		boxVisible.setVisible(true);
		aniadirBtn.setText(bundle.getString("modificar"));
		nombreTxt.setDisable(true);

	}

	public void eliminar() {
		boxVisible.setVisible(true);
		aniadirBtn.setText(bundle.getString("eliminar"));
		ocul.setVisible(false);

	}
}
