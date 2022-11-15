package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.DeportistaDao;
import dao.EquipoDao;
import dao.EventoDao;
import dao.ParticipacionDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.PartiTabla;
import util.Propiedades;
import util.Utilidades;

public class ControladorAniadirParticipacion implements Initializable {

	@FXML
	private Button aniadirBtn;
	private ResourceBundle bundle;
	@FXML
	private Button cancelarBtn;

	@FXML
	private ComboBox<Deportista> cmboDepoirtista;

	@FXML
	private ComboBox<Equipo> cmboEquipo;

	@FXML
	private ComboBox<Evento> cmboEvento;
	@FXML
	private ToggleGroup medalla;

	@FXML
	private RadioButton medallaBronce;

	@FXML
	private RadioButton medallaOro;

	@FXML
	private RadioButton medallaPlata;

	@FXML
	private RadioButton medallaSin;

	@FXML
	private TextField edadTxt;
	private Stage stage;

	private int id_dep_ant;
	private int id_ev_ant;

	@FXML
	void aniadir(ActionEvent event) {
		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			try {
				stage = (Stage) this.cancelarBtn.getScene().getWindow();
				if (stage.getTitle().equals(bundle.getString("aniaPart"))) {
					// Añadir una olimpiada
					int id_deportista = cmboDepoirtista.getSelectionModel().getSelectedItem().getId_deportista();
					int id_evento = cmboEvento.getSelectionModel().getSelectedItem().getId_evento();
					int id_equipo = cmboEquipo.getSelectionModel().getSelectedItem().getId_equipo();
					Boolean b = ParticipacionDao.aniadirParticipacion(id_deportista, id_evento, id_equipo,
							Integer.parseInt(edadTxt.getText()), cualmedalla());
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err7"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info2"));
						stage.close();
					}

				} else {

					// Modificar una olimpiada
					int id_deportista = cmboDepoirtista.getSelectionModel().getSelectedItem().getId_deportista();

					int id_evento = cmboEvento.getSelectionModel().getSelectedItem().getId_evento();
					int id_equipo = cmboEquipo.getSelectionModel().getSelectedItem().getId_equipo();
					Boolean b = ParticipacionDao.modificarParticipacion(id_deportista, id_evento, id_equipo,
							Integer.parseInt(edadTxt.getText()),cualmedalla(), id_dep_ant, id_ev_ant);
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err8"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info3"));
						stage.close();
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
	private String cualmedalla() {
		if(medallaPlata.isSelected())
			return "Silver";
		if(medallaOro.isSelected())
			return "Gold";
		if(medallaBronce.isSelected())
			return "Bronze";
		return "NA";
	}
	private String comprobar() {
		String str = "Error ";
		if (cmboDepoirtista.getSelectionModel().getSelectedItem() == null)
			str += bundle.getString("check1");
		if (cmboEquipo.getSelectionModel().getSelectedItem() == null)
			str += bundle.getString("check2");
		if (cmboEvento.getSelectionModel().getSelectedItem() == null)
			str += bundle.getString("check3");
		if (edadTxt.getText().equals(""))
			str += bundle.getString("check4");
		if (edadTxt.getText().length() > 4)
			str += bundle.getString("err19");
		else {
			try {
				Integer.parseInt(edadTxt.getText());

			} catch (NumberFormatException e) {
				str += bundle.getString("err9");
			}
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
			cmboEvento.setItems(EventoDao.cargarEventos());
			cmboDepoirtista.setItems(DeportistaDao.cargarDeportistas());
			cmboEquipo.setItems(EquipoDao.cargarEquipos());
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err10"));
		}

	}

	public void cargardatos(PartiTabla pt) {
		try {
			cmboDepoirtista.setDisable(true);
			cmboEvento.setDisable(true);
			cmboDepoirtista.getSelectionModel().select(DeportistaDao.cualDeportista(pt.getNombre()));
			cmboEvento.getSelectionModel().select(EventoDao.cualEvento(pt.getEvento()));
			cmboEquipo.getSelectionModel().select(EquipoDao.cualEquipo(pt.getEquipo()));
			edadTxt.setText(pt.getEdad() + "");
			if(pt.getMedalla().equals("NA"))
				medallaSin.setSelected(true);
			if(pt.getMedalla().equals("Gold"))
				medallaOro.setSelected(true);
			if(pt.getMedalla().equals("Silver"))
				medallaPlata.setSelected(true);
			if(pt.getMedalla().equals("Bronze"))
				medallaPlata.setSelected(true);
			id_dep_ant = DeportistaDao.cualDeportista(pt.getNombre()).getId_deportista();
			id_ev_ant = EventoDao.cualEvento(pt.getEvento()).getId_evento();
			aniadirBtn.setText(bundle.getString("modificar"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err10"));
		}
	}

}
