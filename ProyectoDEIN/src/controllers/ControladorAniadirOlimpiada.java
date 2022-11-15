package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import model.Olimpiada;
import util.Propiedades;
import util.Utilidades;

public class ControladorAniadirOlimpiada implements Initializable {
	@FXML
	private ComboBox<Olimpiada> comboOlimpiada;
	@FXML
	private HBox visibleModi;
	@FXML
	private Button aniadirBtn;
	@FXML
	private GridPane grid;
	@FXML
	private TextField anioTxt;

	@FXML
	private Button cancelarBtn;

	@FXML
	private TextField ciudadTxt;

	@FXML
	private TextField nombreTxt;

	@FXML
	private RadioButton rbInvierno;
	private ResourceBundle bundle;
	@FXML
	private RadioButton rbVerano;

	@FXML
	private ToggleGroup season;
	private Stage stage;

	@FXML
	void aniadir(ActionEvent event) {

		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			String temporada;
			if (rbInvierno.isSelected())
				temporada = "Winter";
			else
				temporada = "Summer";
			try {
				stage = (Stage) this.cancelarBtn.getScene().getWindow();
				if (stage.getTitle().equals(bundle.getString("aniaOl"))) {
					// Añadir una olimpiada
					Boolean b = OlimpiadasDao.aniadirOlimpiada(nombreTxt.getText(), Integer.parseInt(anioTxt.getText()),
							temporada, ciudadTxt.getText());
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err11"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info4"));
						stage.close();
					}
				} else {
					if (aniadirBtn.getText().equals(bundle.getString("modificar"))) {
						// Modificar una olimpiada
						Boolean b = OlimpiadasDao.modificarOlimpiada(nombreTxt.getText(),
								Integer.parseInt(anioTxt.getText()), temporada, ciudadTxt.getText(),
								comboOlimpiada.getSelectionModel().getSelectedItem().getNombre());
						if (!b)
							Utilidades.mostrarAlertInfo(stage, bundle.getString("err8"));
						else {
							stage = (Stage) this.cancelarBtn.getScene().getWindow();
							Utilidades.mostrarAlertInfo(stage, bundle.getString("info5"));
							stage.close();
						}
					}
					else {
						//Eliminar
						Boolean b = OlimpiadasDao.eliminarOlimpiada(comboOlimpiada.getSelectionModel().getSelectedItem());
						if(!b) {
							Utilidades.mostrarAlertInfo(stage, bundle.getString("err3"));
						}
						else {
							Utilidades.mostrarAlertInfo(stage, bundle.getString("info18"));
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

	private String comprobar() {
		String str = "Error ";
		if (nombreTxt.getText().equals(""))
			str += bundle.getString("check5");
		if (nombreTxt.getText().length()>11)
			str +=bundle.getString("err19");
		if (ciudadTxt.getText().equals(""))
			str += bundle.getString("check6");
		if (anioTxt.getText().equals(""))
			str += bundle.getString("check7");
		if (anioTxt.getText().length()>6)
			str +=bundle.getString("err19");
		if (ciudadTxt.getText().length()>50)
			str +=bundle.getString("err19");
		else {
			try {
				Integer.parseInt(anioTxt.getText());

			} catch (NumberFormatException e) {
				str += bundle.getString("err9");
			}
		}
		return str;
	}

	@FXML
	void llenar(ActionEvent event) {
		nombreTxt.setDisable(true);
		nombreTxt.setText(comboOlimpiada.getSelectionModel().getSelectedItem().getNombre());
		anioTxt.setText(comboOlimpiada.getSelectionModel().getSelectedItem().getAnio() + "");
		if (comboOlimpiada.getSelectionModel().getSelectedItem().getTemporada().equals("Summer")) {
			rbVerano.setSelected(true);
		} else {
			rbInvierno.setSelected(true);
		}
		ciudadTxt.setText(comboOlimpiada.getSelectionModel().getSelectedItem().getCiudad());
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			comboOlimpiada.setItems(OlimpiadasDao.cargarOlimpiadas());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarDatos() {
		visibleModi.setVisible(true);
		try {
			comboOlimpiada.setItems(OlimpiadasDao.cargarOlimpiadas());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aniadirBtn.setText(bundle.getString("modificar"));

	}

	public void eliminar() {
		visibleModi.setVisible(true);
		aniadirBtn.setText(bundle.getString("eliminar"));
		grid.setVisible(false);
	}
}
