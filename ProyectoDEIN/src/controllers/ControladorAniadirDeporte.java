package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.DeporteDao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Deporte;
import util.Propiedades;
import util.Utilidades;

public class ControladorAniadirDeporte implements Initializable {

	@FXML
	private Button aniadirBtn;

	@FXML
	private HBox boxVisible;
	@FXML
	private HBox boxocul;
	@FXML
	private Button cancelarBtn;
	@FXML
	private Button elimBtn;
	@FXML
	private Button modiBtn;
	@FXML
	private ListView<Deporte> listaDeportes;
	private Stage stage;

	@FXML
	private TextField nombreTxt;

	private ResourceBundle bundle;

	@FXML
	void aniadir(ActionEvent event) {
		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			try {
				stage = (Stage) this.cancelarBtn.getScene().getWindow();

				// Añadir un deporte
				Boolean b = DeporteDao.aniaidirDeporte(nombreTxt.getText());
				if (!b)
					Utilidades.mostrarAlertInfo(stage, bundle.getString("err16"));
				else {
					stage = (Stage) this.cancelarBtn.getScene().getWindow();
					Utilidades.mostrarAlertInfo(stage, bundle.getString("info15"));
					stage.close();
				}

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
	void eliminar(ActionEvent event) {
		if (listaDeportes.getSelectionModel().getSelectedItem() != null) {
			Boolean b;
			try {
				b = DeporteDao.eliminarDeporte(listaDeportes.getSelectionModel().getSelectedItem());
				if (!b) {
					Utilidades.mostrarAlertInfo(stage, bundle.getString("err3"));
				} else {
					Utilidades.mostrarAlertInfo(stage, bundle.getString("info17"));
					stage = (Stage) this.cancelarBtn.getScene().getWindow();
					stage.close();
				}
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err3"));
			}

		} else {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err18"));
		}
	}

	@FXML
	void modificar(ActionEvent event) {
		if (listaDeportes.getSelectionModel().getSelectedItem() != null) {
			Boolean b;
			try {
				b = DeporteDao.modificarDeporte(nombreTxt.getText(),
						listaDeportes.getSelectionModel().getSelectedItem().getNombre());
				if (!b)
					Utilidades.mostrarAlertInfo(stage, bundle.getString("err17"));
				else {
					stage = (Stage) this.cancelarBtn.getScene().getWindow();
					Utilidades.mostrarAlertInfo(stage, bundle.getString("info16"));
					stage.close();
				}
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err17"));
			}
		} else {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err18"));
		}

	}

	@FXML
	void llenar(MouseEvent event) {
		if (listaDeportes.getSelectionModel().getSelectedItem() != null) {
			nombreTxt.setText(listaDeportes.getSelectionModel().getSelectedItem().getNombre());
			modiBtn.setDisable(false);
			elimBtn.setDisable(false);
			aniadirBtn.setDisable(true);
		}
	}

	private String comprobar() {
		String str = "Error ";
		if (nombreTxt.getText().equals(""))
			str += bundle.getString("check5");
		if (nombreTxt.getText().length()>100)
			str +=bundle.getString("err19");

		return str;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			listaDeportes.setItems(DeporteDao.cargarDeportes());
			

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
		boxocul.setVisible(false);

	}
}
