package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.ProductosDao;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Producto;

public class ControladorVentanaPrincipal implements Initializable {
	@FXML
	private TableView<Producto> tabla;
	@FXML
	private TableColumn<Producto, String> codigoCol;

	@FXML
	private TableColumn<Producto, Boolean> disponibleCol;
	@FXML
	private TableColumn<Producto, String> nombreCol;

	@FXML
	private TableColumn<Producto, Float> precioCol;
	@FXML
	private TextField codigoTxt;

	@FXML
	private Button crearBtn;
	@FXML
	private Button actualizarBtn;

	@FXML
	private Button seleccionarBtn;
	@FXML
	private Button limpiarBtn;
	@FXML
	private CheckBox disponibleChkBox;

	@FXML
	private TextField nombreTxt;

	@FXML
	private TextField precioTxt;

	@FXML
	private ImageView imagenProducto;
	private Image image = null;
	private InputStream foto;
	private InputStream fotoantes;
	private Stage stage;
	private ContextMenu ctxMenu;

	private ObservableList<Producto> lstProductos;

	@FXML
	void crear(ActionEvent event) {
		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			int disponible;
			if (disponibleChkBox.isSelected()) {
				disponible = 1;
			} else {
				disponible = 0;
			}
			Boolean b;
			try {
				b = ProductosDao.aniadirProducto(codigoTxt.getText(), nombreTxt.getText(),
						Float.parseFloat(precioTxt.getText()), disponible, foto);
				if (!b)
					mostrarAlertInfo(stage, "Error al introducir producto");
				else {
					mostrarAlertInfo(stage, "El producto ha sido añadido");
					try {
						tabla.setItems(ProductosDao.cargarTabla());
					} catch (SQLException e) {
						mostrarAlertInfo(stage, "Error al recargar elementos de la base");
					}
					tabla.refresh();

				}
			} catch (Exception e1) {
				mostrarAlertInfo(stage, "Error en la base de datos");
			}
		} else {
			// tiene algun error
			mostrarAlertInfo(stage, str);
		}
	}

	@FXML
	void seleccionado(MouseEvent event) {
		if (tabla.getSelectionModel().getSelectedItem() != null) {
			// ha seleccionado un elemento de la lista
			codigoTxt.setDisable(true);
			actualizarBtn.setDisable(false);
			crearBtn.setDisable(true);
			llenarDatos(tabla.getSelectionModel().getSelectedItem());
			try {
				fotoantes =ProductosDao.dameLaFoto(tabla.getSelectionModel().getSelectedItem().getCodigo());
			} catch (SQLException e) {
				mostrarAlertInfo(stage, "no se ha podido recuperar la imagen");
			}
		}
	}

	private void llenarDatos(Producto producto) {
		imagenProducto.setVisible(true);
		codigoTxt.setText(producto.getCodigo());
		nombreTxt.setText(producto.getNombre());
		precioTxt.setText(producto.getPrecio() + "");
		if (producto.getDisponible() == 1)
			disponibleChkBox.setSelected(true);
		else
			disponibleChkBox.setSelected(false);
		if (producto.getImagen() != null) {
			try {
				imagenProducto.setImage(new Image(ProductosDao.dameLaFoto(producto.getCodigo())));
			} catch (SQLException e) {
				mostrarAlertInfo(stage, "no se ha podido recuperar la imagen");
			}
		}
		else {
			foto =null;
			imagenProducto.setVisible(false);
		}
	}

	@FXML
	void actualizar(ActionEvent event) {
		String str = comprobar();
		if (str.equals("Error ")) {
			// No ha dado ningun error
			int disponible;
			if (disponibleChkBox.isSelected()) {
				disponible = 1;
			} else {
				disponible = 0;
			}
			if(foto==null)
				foto=fotoantes;
			Boolean b;
			try {
				b = ProductosDao.modificarProducto(codigoTxt.getText(), nombreTxt.getText(),
						Float.parseFloat(precioTxt.getText()), disponible, foto);
				if (!b)
					mostrarAlertInfo(stage, "Error al introducir producto");
				else {
					mostrarAlertInfo(stage, "El producto ha sido modificado");
					try {
						tabla.setItems(ProductosDao.cargarTabla());
						tabla.refresh();
					} catch (SQLException e) {
						mostrarAlertInfo(stage, "Error al recargar elementos de la base");
					}

				}
			} catch (Exception e1) {
				mostrarAlertInfo(stage, "Error en la base de datos");
			}
		} else {
			// tiene algun error
			mostrarAlertInfo(stage, str);
		}

	}

	@FXML
	void limpiar(ActionEvent event) {
		codigoTxt.setText("");
		nombreTxt.setText("");
		precioTxt.setText("");
		disponibleChkBox.setSelected(false);
		codigoTxt.setDisable(false);
		actualizarBtn.setDisable(true);
		crearBtn.setDisable(false);
		imagenProducto.setVisible(false);
		tabla.getSelectionModel().clearSelection();
	}

	void limpiar() {
		codigoTxt.setText("");
		nombreTxt.setText("");
		precioTxt.setText("");
		disponibleChkBox.setSelected(false);
		codigoTxt.setDisable(false);
		actualizarBtn.setDisable(true);
		crearBtn.setDisable(false);
		tabla.getSelectionModel().clearSelection();
	}

	@FXML
	void seleccionarImagen(ActionEvent event) {
		FileChooser elegir = new FileChooser();
		elegir.setTitle("Elegir imagen");
		String currentPath = Paths.get("./resources/images").toAbsolutePath().normalize().toString();
		elegir.setInitialDirectory(new File(currentPath));
		// Set extension filter
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.jpeg","*.png");
		
		elegir.getExtensionFilters().addAll(extFilterJPG);

		// Show open file dialog
		File file = elegir.showOpenDialog(null);

		if (file != null) {
			imagenProducto.setVisible(true);
			image = new Image(file.toURI().toString());
			imagenProducto.setImage(image);
			try {
				foto = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				mostrarAlertInfo(stage, "Error al cargar imagen");
			}
		} else {
			imagenProducto.setVisible(false);
		}
	}

	private String comprobar() {
		String str = "Error ";
		if (nombreTxt.getText().equals(""))
			str += "-> El campo nombre no puede estar vacio\n";
		if (precioTxt.getText().equals(""))
			str += "-> El campo precio no puede estar vacio\n";
		if (codigoTxt.getText().length() != 5)
			str += "-> El codigo tiene que tener 5 caracteres\n";
		try {
			Float.parseFloat(precioTxt.getText());
		} catch (NumberFormatException e) {
			str += "-> El precio tiene que ser un numero decilmal\n";
		}
		return str;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MenuItem verImagen = new MenuItem("Ver imagen");
		MenuItem elimitem = new MenuItem("Eliminar");
		ctxMenu = new ContextMenu();
		ctxMenu.getItems().addAll(verImagen, elimitem);
		tabla.setContextMenu(ctxMenu);
		verImagen.setOnAction(e -> verImagen(e, tabla.getSelectionModel().getSelectedItem()));
		elimitem.setOnAction(e -> eliminar(e));
		lstProductos = FXCollections.observableArrayList();
		try {
			lstProductos = ProductosDao.cargarTabla();
			// columnas
			codigoCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
			nombreCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
			precioCol.setCellValueFactory(new PropertyValueFactory<Producto, Float>("precio"));
			disponibleCol.setCellValueFactory(new PropertyValueFactory<Producto, Boolean>("disponible"));

			// colocar columnas
			codigoCol.prefWidthProperty().bind(tabla.widthProperty().multiply(0.25));
			nombreCol.prefWidthProperty().bind(tabla.widthProperty().multiply(0.25));
			precioCol.prefWidthProperty().bind(tabla.widthProperty().multiply(0.25));
			disponibleCol.prefWidthProperty().bind(tabla.widthProperty().multiply(0.25));
			
			disponibleCol.setCellValueFactory(
					cellData -> {
						Producto p = cellData.getValue();
						Boolean v = (p.getDisponible() == 1);
						return new ReadOnlyBooleanWrapper(v);
					});
			disponibleCol.setCellFactory(
					CheckBoxTableCell.<Producto>forTableColumn(disponibleCol));
			// Alinear columnas
			precioCol.setStyle( "-fx-alignment: CENTER-RIGHT;");
			disponibleCol.setStyle( "-fx-alignment: CENTER;");

			tabla.setItems(lstProductos);
			
			
		} catch (SQLException e) {

			mostrarAlertInfo(stage, "Error al cargar elementos de la base");
		}

	}

	private void eliminar(ActionEvent e) {

		try {
			Boolean b = ProductosDao.eliminarProducto(tabla.getSelectionModel().getSelectedItem().getCodigo());
			if (b)
				try {
					tabla.setItems(ProductosDao.cargarTabla());
					tabla.refresh();
					limpiar();
					mostrarAlertInfo(stage, "Producto eliminado correctamente");
				} catch (SQLException e1) {
					mostrarAlertInfo(stage, "Error al recargar elementos de la base");
				}
		} catch (SQLException e1) {
			mostrarAlertInfo(stage, "Error al eliminar producto de la base");
		}

	}

	private void verImagen(ActionEvent e, Producto producto) {
		if (producto.getImagen() != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventanaModalImagen.fxml"));
			try {
				Parent root = loader.load();
				Scene newScene = new Scene(root);
				Stage newStage = new Stage();
				newStage.setResizable(false);
				newStage.initModality(Modality.APPLICATION_MODAL);
				newStage.initOwner(this.crearBtn.getScene().getWindow());
				newStage.setScene(newScene);
				newStage.setTitle("Imagen");
				Image image = new Image(getClass().getResource("/images/carrito.png").toString());
				newStage.getIcons().add(image);
				ControladorVentanaModalImagen controlador = loader.getController();
				controlador.cargarImagen(new Image(ProductosDao.dameLaFoto(producto.getCodigo())));
				newStage.showAndWait();
			} catch (IOException | SQLException e2) {
				mostrarAlertInfo(stage, "Error al abrir la ventana");
			}
		} else {
			mostrarAlertInfo(stage, "Imagen no disponible para ese producto");
		}
	}

	private void mostrarAlertInfo(Window win, String str) {
		Alert alert;
		if (str.contains("Error")) {
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
		} else {
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Info");
		}
		alert.initOwner(win);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.showAndWait();
	}
	@FXML
    void lanzarInfo(ActionEvent event) {
		mostrarAlertInfo(stage, "Gestion de productos 1.0\nAutor: Irune Guinea");
    }

}
