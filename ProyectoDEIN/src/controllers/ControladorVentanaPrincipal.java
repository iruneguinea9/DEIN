package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.DeporteDao;
import dao.DeportistaDao;
import dao.EventoDao;
import dao.OlimpiadasDao;
import dao.ParticipacionDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Deporte;
import model.Olimpiada;
import model.PartiTabla;
import model.Participacion;
import util.Propiedades;
import util.Utilidades;

public class ControladorVentanaPrincipal implements Initializable {
	@FXML
	private TableView<PartiTabla> tablaParti;
	@FXML
	private MenuItem aniadirDeporte;
	private ContextMenu ctxMenu;
	@FXML
	private MenuItem aniadirDeportista;

	@FXML
	private MenuItem aniadirEquipo;

	@FXML
	private MenuItem aniadirOlimpiada;

	@FXML
	private MenuItem aniaidrEvento;
	private ResourceBundle bundle;
	@FXML
	private ComboBox<Deporte> elegirDeportes;

	@FXML
	private ComboBox<Olimpiada> elegirOlimpiada;

	@FXML
	private MenuItem eliminarDeporte;

	@FXML
	private MenuItem eliminarDeportista;

	@FXML
	private MenuItem eliminarEquipo;

	@FXML
	private MenuItem eliminarEvento;

	@FXML
	private MenuItem eliminarOlimpiada;

	@FXML
	private TextField filtro;
    @FXML
    private GridPane gridGrande;
	@FXML
	private MenuItem modificarDeporte;

	@FXML
	private MenuItem modificarDeportista;

	@FXML
	private MenuItem modificarEquipo;

	@FXML
	private MenuItem modificarEvento;

	@FXML
	private MenuItem modificarOlimpiada;

	@FXML
	private Button mostrarBtn;
	@FXML
	private Button modificarParticipacion;
    @FXML
    private MenuBar menubar;
	@FXML
	private Button aniadirParticipacion;
	@FXML
	private Button eliminarParticipacion;
	   @FXML
	    private Button resetBtn;

	// columnas
	@FXML
	private TableColumn<PartiTabla, String> nombreCol;

	@FXML
	private TableColumn<PartiTabla, Integer> pesoCol;

	@FXML
	private TableColumn<PartiTabla, String> sexoCol;
	@FXML
	private TableColumn<PartiTabla, ImageView> fotoCol;

	@FXML
	private TableColumn<PartiTabla, String> medallaCol;

	@FXML
	private TableColumn<PartiTabla, String> equipoCol;

	@FXML
	private TableColumn<PartiTabla, String> eventoCol;

	@FXML
	private TableColumn<PartiTabla, Integer> edadCol;
	@FXML
	private TableColumn<PartiTabla, Integer> alturaCol;

	private Stage stage;
	private ObservableList<PartiTabla> lstPartiTablas;

	@FXML
	void gestionarDeporte(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirDeporte.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("aniaDep"));
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void aniadirDeportista(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirDeportista.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("aniaDepta"));
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void aniadirEquipo(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirEquipo.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("aniaEquip"));
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void aniadirEvento(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirEvento.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("aniaEv"));
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void aniadirOlimpiada(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirOlimpiada.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("aniaOl"));
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void modificarOlimpiada(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirOlimpiada.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirOlimpiada controlador = loader.getController();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("modiOl"));
			controlador.cargarDatos();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void eliminarDeportista(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirDeportista.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirDeportista controlador = loader.getController();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("elimDepta"));
			controlador.eliminar();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void eliminarEquipo(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirEquipo.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirEquipo controlador = loader.getController();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("elimEq"));
			controlador.eliminar();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void eliminarEvento(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirEvento.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirEvento controlador = loader.getController();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("elimEv"));
			controlador.eliminar();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void eliminarOlimpiada(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirOlimpiada.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirOlimpiada controlador = loader.getController();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("elimOl"));
			controlador.eliminar();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}
	

	@FXML
	void fitlrar(KeyEvent event) {
		ObservableList<PartiTabla> lista2 = FXCollections.observableArrayList();
		if (!filtro.getText().isBlank()) {
			for (int i = 0; i < lstPartiTablas.size(); i++) {
				if (lstPartiTablas.get(i).getNombre().toLowerCase().contains(filtro.getText().toLowerCase())) {
					lista2.add(lstPartiTablas.get(i));
				}
			}
			tablaParti.setItems(lista2);

		} else {
			lista2.clear();
			tablaParti.setItems(lstPartiTablas);
		}
	}
	  @FXML
	    void reset(ActionEvent event) {
		  tablaParti.setItems(lstPartiTablas);
		  filtro.setText("");
		  elegirDeportes.getSelectionModel().select(null);
		  elegirOlimpiada.getSelectionModel().select(null);
	    }


	@FXML
	void modificarDeportista(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirDeportista.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirDeportista controlador = loader.getController();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("modiDepta"));
			controlador.modificar();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void modificarEquipo(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirEquipo.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirEquipo controlador = loader.getController();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("modiEquip"));
			controlador.modificar();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void modificarEvento(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirEvento.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			ControladorAniadirEvento controlador = loader.getController();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("modiEv"));
			controlador.modificar();
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		}
	}

	@FXML
	void aniadirParticipacion(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirParti.fxml"),bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.mostrarBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("aniaPart"));
			tablaParti.refresh();
			newStage.showAndWait();
			lstPartiTablas = ParticipacionDao.cargarTabla("");
			tablaParti.setItems(lstPartiTablas);
			tablaParti.refresh();
		} catch (IOException e) {
			e.printStackTrace();
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err1") );
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err2") );
			e.printStackTrace();
		}
	}

	@FXML
	void eliminarParticipacion(ActionEvent event) {
		if (tablaParti.getSelectionModel().getSelectedItem() != null) {
			try {
				Participacion p = ParticipacionDao.cualParticipacion(DeportistaDao.cualDeportista(tablaParti.getSelectionModel().getSelectedItem().getNombre()),EventoDao.cualEvento(tablaParti.getSelectionModel().getSelectedItem().getEvento()));
				boolean b=ParticipacionDao.eliminarParticipacion(p);
				if(!b) {
					Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
				}
			} catch (SQLException e1) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
			}
			try {
				lstPartiTablas = ParticipacionDao.cargarTabla("");
				tablaParti.setItems(lstPartiTablas);
				tablaParti.refresh();				
				Utilidades.mostrarAlertInfo(stage, bundle.getString("info1"));
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err3"));
			}
			
		}
		else {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err4"));
		}
	}

	@FXML
	void modificarParticipacion(ActionEvent event) {
		if (tablaParti.getSelectionModel().getSelectedItem() != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirParti.fxml"),bundle);
			try {
				Parent root = loader.load();
				Scene newScene = new Scene(root);
				Stage newStage = new Stage();
				ControladorAniadirParticipacion controlador = loader.getController();

				newStage.setResizable(false);
				newStage.initModality(Modality.APPLICATION_MODAL);
				newStage.initOwner(this.mostrarBtn.getScene().getWindow());
				newStage.setScene(newScene);
				newStage.setTitle(bundle.getString("modiPart"));
				controlador.cargardatos(tablaParti.getSelectionModel().getSelectedItem());
				newStage.showAndWait();
				lstPartiTablas = ParticipacionDao.cargarTabla("");
				tablaParti.setItems(lstPartiTablas);
				tablaParti.refresh();
			} catch (IOException e) {
				Utilidades.mostrarAlertInfo(stage,bundle.getString("err1"));
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage,bundle.getString("err2") );
			}
		} else {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err5"));
		}
	}

	@FXML
	void mostrarParticipaciones(ActionEvent event) {
		String filtro = "";
		if (elegirOlimpiada.getSelectionModel().getSelectedItem() == null) {
			// no ha elegido olimpiada
			if (elegirDeportes.getSelectionModel().getSelectedItem() == null) {
				// tampoco ha elegido deporte
			} else {
				Deporte d = elegirDeportes.getSelectionModel().getSelectedItem();
				filtro += " and olimpiadas.Deporte.id_deporte = " + d.getId_deporte() + ";";
			}
		} else {
			// si ha elegido olimpiada
			if (elegirDeportes.getSelectionModel().getSelectedItem() == null) {
				// no ha elegido deporte
				Olimpiada o = elegirOlimpiada.getSelectionModel().getSelectedItem();
				filtro += " and olimpiadas.Olimpiada.id_olimpiada = " + o.getId_olimpiada() + ";";
			} else {
				// tambien ha elegido deporte
				Olimpiada o = elegirOlimpiada.getSelectionModel().getSelectedItem();
				Deporte d = elegirDeportes.getSelectionModel().getSelectedItem();
				filtro += " and olimpiadas.Olimpiada.id_olimpiada = " + o.getId_olimpiada()
						+ " and olimpiadas.Deporte.id_deporte = " + d.getId_deporte() + ";";
			}
		}
		try {
			tablaParti.setItems(ParticipacionDao.cargarTabla(filtro));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err6"));
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			MenuItem elimitem = new MenuItem("Eliminar");
			ctxMenu = new ContextMenu();
			ctxMenu.getItems().addAll(elimitem);
			tablaParti.setContextMenu(ctxMenu);
			elimitem.setOnAction(e -> eliminarParticipacion(e));
			elegirOlimpiada.setItems(OlimpiadasDao.cargarOlimpiadas());
			elegirDeportes.setItems(DeporteDao.cargarDeportes());
			lstPartiTablas = FXCollections.observableArrayList();
			lstPartiTablas = ParticipacionDao.cargarTabla("");
			
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");

			// columnas
			fotoCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, ImageView>("foto"));
			nombreCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, String>("nombre"));
			sexoCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, String>("sexo"));
			pesoCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, Integer>("peso"));
			alturaCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, Integer>("altura"));
			edadCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, Integer>("edad"));
			equipoCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, String>("equipo"));
			eventoCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, String>("evento"));
			medallaCol.setCellValueFactory(new PropertyValueFactory<PartiTabla, String>("medalla"));

			// colocar columnas
			fotoCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.12));
			nombreCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.1));
			sexoCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.1));
			pesoCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.1));
			alturaCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.1));
			edadCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.1));
			equipoCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.1));
			eventoCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.16));
			medallaCol.prefWidthProperty().bind(tablaParti.widthProperty().multiply(0.1));

			
			fotoCol.setStyle( "-fx-alignment: CENTER;");
			nombreCol.setStyle( "-fx-alignment: CENTER;");
			sexoCol.setStyle( "-fx-alignment: CENTER;");
			pesoCol.setStyle( "-fx-alignment: CENTER-RIGHT;");
			alturaCol.setStyle( "-fx-alignment: CENTER-RIGHT;");
			edadCol.setStyle( "-fx-alignment: CENTER-RIGHT;");
			equipoCol.setStyle( "-fx-alignment: CENTER;");
			eventoCol.setStyle( "-fx-alignment: CENTER;");
			medallaCol.setStyle( "-fx-alignment: CENTER;");
			
			GridPane.setVgrow(gridGrande, Priority.ALWAYS);
			GridPane.setHgrow(gridGrande, Priority.ALWAYS);
			GridPane.setVgrow(tablaParti, Priority.ALWAYS);
			GridPane.setHgrow(tablaParti, Priority.ALWAYS);
			
			tablaParti.setItems(lstPartiTablas);
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage,bundle.getString("err2") );
		}

	}

}
