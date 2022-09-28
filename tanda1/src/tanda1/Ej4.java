package tanda1;

import java.awt.FlowLayout;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Persona;


public class Ej4 extends Application{
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		

		
		// Boton
		FlowPane boton = new FlowPane();
		Button agregarBtn = new Button("Agregar Persona");
		
		boton.getChildren().add(agregarBtn);
		boton.setAlignment(Pos.BASELINE_CENTER);
	
		// Tabla
		
		TableView<Persona> tabla = new TableView<>();
		
		// columna nombre
		TableColumn<Persona, String> nombreCol = new TableColumn<>("Nombre");
		nombreCol.setCellValueFactory(
				new PropertyValueFactory<>("nombre"));
		
		// columna nombre
		TableColumn<Persona, String> apellidosCol = new TableColumn<>("Apellidos");
		apellidosCol.setCellValueFactory(
				new PropertyValueFactory<>("apellidos"));
				
		// columna nombre
		TableColumn<Persona, String> edadCol = new TableColumn<>("Edad");
		edadCol.setCellValueFactory(
				new PropertyValueFactory<>("edad"));
		edadCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		tabla.getColumns().addAll(nombreCol,apellidosCol,edadCol);
		nombreCol.prefWidthProperty().bind(tabla.widthProperty().multiply(0.4));
		apellidosCol.prefWidthProperty().bind(tabla.widthProperty().multiply(0.4));
		edadCol.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));

		

		Node right = null;
		Node left = null;
		Node top = null;
		BorderPane root = new BorderPane(tabla, top, right, boton, left);

		agregarBtn.setOnAction(e -> abrirVentana(tabla));
		
		Image image = new Image(getClass().getResource("/picture/agenda.png").toString());
		stage.getIcons().add(image);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("PERSONAS");
		stage.show();
	}

	private void abrirVentana(TableView tabla) {
		TextField fNameField = new TextField();
        TextField lNameField = new TextField();
        TextField edadFld = new TextField();
        edadFld.setPrefColumnCount(10);
        GridPane root = new GridPane();
        
        Button guardar = new Button("Guardar");
        Button cancelar = new Button("Cancelar");
        
        root.setHgap(10);
        root.setVgap(5);
        root.addRow(0, new Label("Nombre:"), fNameField);
        root.addRow(1, new Label("Apellido:"), lNameField);
        root.addRow(2, new Label("Edad:"), edadFld);
        root.addRow(3,guardar,cancelar);
        root.setStyle("-fx-padding: 20;");
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.setTitle("Nueva persona");
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
        newStage.setResizable(false);
        
        
        guardar.setOnAction(e -> {
        	String str = comprobar(fNameField.getText(),lNameField.getText(),edadFld.getText());
			 if(str.equals("")) {
				 Persona p = new Persona(fNameField.getText(),lNameField.getText(),Integer.parseInt(edadFld.getText()));
				 if(!estaRepe(p,tabla)) {
					 ObservableList<Persona> personas =  tabla.getItems();
					 personas.add(p);
					 mostrarAlertInfo(newStage,"Persona agregada correctamente");
					 limpiar(fNameField,lNameField,edadFld);
					 newStage.close();
				 }
				 else {
					 mostrarAlertInfo(newStage,"Error La persona ya está en la tabla");
				 }
			 }
			 else {
				 mostrarAlertInfo(newStage,str);
			 
			 }
        });
        
        cancelar.setOnAction(e -> newStage.close());
        
        
	}

	private boolean estaRepe(Persona p1,TableView<Persona> tabla) {
		ObservableList<Persona> personas =  tabla.getItems();
		for(int i = 0;i<personas.size();i++) {
			if(personas.get(i).equals(p1)) {
				return true;
			}
		}
		
		return false;
	}

	private String comprobar(String nom, String ape, String edad) {
		String str = "";
		if(nom.isBlank())
			str += "Error -> Tiene que introducir un nombre\n";
		if(ape.isBlank())
			str += "Error -> Tiene que introducir un apellido\n";
		try {
		Integer.parseInt(edad);
		}catch (NumberFormatException e) {
			str += "Error -> Tiene que introducir una edad correcta\n";
		}
		return str;
	}

	private void mostrarAlertInfo(Window win,String str) {
		Alert alert;
		if(str.contains("Error")) {
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
		}
		else {
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Info");
		}
		alert.initOwner(win);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.showAndWait();
	}
	private void limpiar(TextField nom,TextField ape,TextField edad) {
		nom.setText("");
		ape.setText("");
		edad.setText("");
		
	}
}
