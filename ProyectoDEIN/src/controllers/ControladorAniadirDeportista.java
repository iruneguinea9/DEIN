package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.DeportistaDao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.Deportista;
import util.Propiedades;
import util.Utilidades;

public class ControladorAniadirDeportista implements Initializable {
	private ResourceBundle bundle;
	@FXML
	private TextField alturaTxt;

	@FXML
	private Button aniadirBtn;

	@FXML
	private Button cancelarBtn;
	@FXML
	private GridPane grid;
	@FXML
	private Button elegirBtn;

	@FXML
	private ImageView imagen;
	@FXML
	private ToggleGroup sexo;

	@FXML
	private TextField nombreTxt;

	@FXML
	private TextField pesoTxt;
	@FXML
	private ComboBox<Deportista> cmboDeportista;

	@FXML
	private HBox boxVisible;
	@FXML
	private RadioButton rbFem;

	@FXML
	private RadioButton rbMasc;
	private Stage stage;
	private Image image=null;
	private InputStream foto;

	@FXML
	void aniadirDeportista(ActionEvent event) {
		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			String sexo;
			if (rbFem.isSelected())
				sexo = "F";
			else
				sexo = "M";
			try {
				stage = (Stage) this.cancelarBtn.getScene().getWindow();
				if (stage.getTitle().equals(bundle.getString("aniaDepta"))) {
					// Añadir un deportista
					Boolean b = DeportistaDao.aniaidrDeportista(nombreTxt.getText(),
							Integer.parseInt(pesoTxt.getText()), Integer.parseInt(alturaTxt.getText()), sexo,foto);
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err14"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info12"));
						stage.close();
					}
				} else {
					if (aniadirBtn.getText().equals(bundle.getString("modificar"))) {
						if (foto==null) {
							URL imageUrl = new URL(getClass().getResource("/images/avatar.jpg").toString());
							foto = new URL(imageUrl.toString()).openStream();
						}
						// Modificar un deportista
						Boolean b = DeportistaDao.modificarDeportista(nombreTxt.getText(),
								Integer.parseInt(pesoTxt.getText()), Integer.parseInt(alturaTxt.getText()), sexo,foto,
								cmboDeportista.getSelectionModel().getSelectedItem().getNombre());
						if (!b)
							Utilidades.mostrarAlertInfo(stage, bundle.getString("err8"));
						else {
							stage = (Stage) this.cancelarBtn.getScene().getWindow();
							Utilidades.mostrarAlertInfo(stage, bundle.getString("info13"));
							stage.close();
						}
					}
					else {
						// eliminar
						// eliminar
						Boolean b = DeportistaDao.eliminarDeportista(cmboDeportista.getSelectionModel().getSelectedItem());
						if(!b) {
							Utilidades.mostrarAlertInfo(stage,bundle.getString("err3"));
						}
						else {
							Utilidades.mostrarAlertInfo(stage,bundle.getString("info14"));
							stage.close();
						}
					}
				}
			} catch (NumberFormatException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err9"));
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err8"));
			} catch (FileNotFoundException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err15"));
			} catch (MalformedURLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err15"));
			} catch (IOException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err15"));
			}
		} else {
			// tiene algun error
			Utilidades.mostrarAlertInfo(stage, str);
		}
	}

	@FXML
	void llenar(ActionEvent event) {
		nombreTxt.setText(cmboDeportista.getSelectionModel().getSelectedItem().getNombre());
		pesoTxt.setText(cmboDeportista.getSelectionModel().getSelectedItem().getPeso() + "");
		if (cmboDeportista.getSelectionModel().getSelectedItem().getSexo().equals("F")) {
			rbFem.setSelected(true);
		} else {
			rbMasc.setSelected(true);
		}
		alturaTxt.setText(cmboDeportista.getSelectionModel().getSelectedItem().getAltura() + "");
		InputStream foto = cmboDeportista.getSelectionModel().getSelectedItem().getFoto();
		if(foto!=null) 
			imagen.setImage(new Image(foto));
	}

	@FXML
	void cancelar(ActionEvent event) {
		stage = (Stage) this.cancelarBtn.getScene().getWindow();
		stage.close();
	}

	@FXML
	void elegirFoto(ActionEvent event) {
		FileChooser elegir = new FileChooser();
		elegir.setTitle(bundle.getString("elegirFoto"));
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString(); 
		elegir.setInitialDirectory(new File(currentPath));
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Archivos JPG", "*.jpg", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        elegir.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = elegir.showOpenDialog(null);

        if (file != null) {
            image = new Image(file.toURI().toString());
            imagen.setImage(image);
            try {
				foto = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err15"));
			}
        }
        else {
        	try {
        		URL imageUrl = new URL(getClass().getResource("/images/avatar.jpg").toString());
        		foto = new URL(imageUrl.toString()).openStream();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err15"));
			} catch (MalformedURLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err15"));
			} catch (IOException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err15"));
			}
        }
	}

	private String comprobar() {
		String str = "Error ";
		if (nombreTxt.getText().equals(""))
			str +=bundle.getString("check5");
		if (nombreTxt.getText().length()>100)
			str +=bundle.getString("err19");
		if (alturaTxt.getText().equals(""))
			str += bundle.getString("check12");
		if (pesoTxt.getText().length()>11)
			str +=bundle.getString("err19");
		if (alturaTxt.getText().length()>11)
			str +=bundle.getString("err19");
		else {
			try {
				Integer.parseInt(alturaTxt.getText());

			} catch (NumberFormatException e) {
				str += bundle.getString("err9");
			}
		}
		if (pesoTxt.getText().equals(""))
			str += bundle.getString("check13");
		else {
			try {
				Integer.parseInt(pesoTxt.getText());

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
			this.imagen.setImage(new Image(getClass().getResource("/images/avatar.jpg").toString()));
			cmboDeportista.setItems(DeportistaDao.cargarDeportistas());
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err10"));
		}

	}

	public void modificar() {
		boxVisible.setVisible(true);
		nombreTxt.setDisable(true);
		aniadirBtn.setText(bundle.getString("modificar"));

	}
	public void eliminar() {
		boxVisible.setVisible(true);
		aniadirBtn.setText(bundle.getString("eliminar"));
		grid.setVisible(false);

	}
}
