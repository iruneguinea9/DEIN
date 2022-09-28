package tanda1;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Persona;


public class Ej2 extends Application{
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		
		// Nombre 
		Label nombreLbl = new Label("Nombre");
		TextField nombreFld = new TextField();
		nombreLbl.setStyle("-fx-padding: 50 0 20 0;");
		
		// Apellidos
		Label apellidosLbl = new Label("Apellidos");
		TextField apellidosFld = new TextField();
		apellidosLbl.setStyle("-fx-padding: 20 0 20 0;");
		
		// Edad
		Label edadLbl = new Label("Edad");
		TextField edadFld = new TextField();
		edadLbl.setStyle("-fx-padding: 20 0 20 0;");
		
		// Boton
		
		Button agregarBtn = new Button("Agregar Persona");
		
		// VBox izquierda
		
		VBox panIz = new VBox(nombreLbl,nombreFld,apellidosLbl,apellidosFld,edadLbl,edadFld, agregarBtn);
		panIz.setVgrow(nombreFld, Priority.ALWAYS);
		panIz.setAlignment(Pos.CENTER);
		panIz.setStyle("-fx-padding: 0 20 0 20;");
		
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
		// Eventos
		 agregarBtn.setOnAction(e -> {
			 String str = comprobar(nombreFld.getText(),apellidosFld.getText(),edadFld.getText());
			 if(str.equals("")) {
				 Persona p1 = new Persona(nombreFld.getText(),apellidosFld.getText(),Integer.parseInt(edadFld.getText()));
				 if(!estaRepe(p1,tabla)) {
				 //if(!tabla.getItems().contains(p1)) {
					 ObservableList<Persona> personas =  tabla.getItems();
					 personas.add(p1);
					 mostrarAlertInfo(stage,"Persona agregada correctamente");
				 }
				 else {
					 mostrarAlertInfo(stage,"Error La persona ya está en la tabla");
				 }
			 }
			 else {
				 mostrarAlertInfo(stage,str);
			 }
		 });
		
		
		GridPane root = new GridPane();
		
		root.add(panIz, 0, 0);
		root.add(tabla, 1, 0);
		GridPane.setHgrow(tabla, Priority.ALWAYS);
		GridPane.setVgrow(tabla, Priority.ALWAYS);
		
		Image image = new Image(getClass().getResource("/picture/agenda.png").toString());
		stage.getIcons().add(image);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("PERSONAS");
		stage.show();
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
			str += "Error ->Tiene que introducir un nombre\n";
		if(ape.isBlank())
			str += "Error ->Tiene que introducir un apellido\n";
		try {
		Integer.parseInt(edad);
		}catch (NumberFormatException e) {
			str += "Erro ->Tiene que introducir una edad correcta\n";
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
	
}
