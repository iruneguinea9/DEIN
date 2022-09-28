package tanda1;


import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;

import javafx.application.Application;
import javafx.collections.FXCollections;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Persona;


public class Ej6 extends Application{
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		
		FlowPane filtros = new FlowPane(5,10);
		Label filtroLbl = new Label("Filtrar por nombre: ");
		TextField filtroFld = new TextField();
		filtros.getChildren().addAll(filtroLbl,filtroFld);
		filtros.setStyle("-fx-padding: 10;");
		
		// Boton
		FlowPane boton = new FlowPane(10,10);
		Button agregarBtn = new Button("Agregar Persona");
		Button modiBtn = new Button("Modificar Persona");
		Button elimBtn = new Button("Eliminar Persona");
		Button importBtn = new Button("Importar");
		Button exportBtn = new Button("Exportar");
		boton.getChildren().addAll(agregarBtn,modiBtn,elimBtn,importBtn,exportBtn);
		boton.setAlignment(Pos.BOTTOM_CENTER);
		boton.setStyle("-fx-padding: 10;");
	
		// Tabla
		
		TableView<Persona> tabla = new TableView<>();
		tabla.setStyle("-fx-margin: 0 0 20 0;");
		tabla.setStyle("-fx-padding: 5;");
		
		
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
		BorderPane root = new BorderPane(tabla, filtros, right, boton, left);
		
		agregarBtn.setOnAction(e -> {
			abrirVentana(tabla,1); //1 para guardar
			}); 
		modiBtn.setOnAction(e -> abrirVentana(tabla,2));  //2 para modificar
		elimBtn.setOnAction(e -> {
			if(tabla.getSelectionModel().getSelectedItem()!=null) {
				
				tabla.getItems().remove(tabla.getSelectionModel().getSelectedIndex());
				tabla.refresh();
				mostrarAlertInfo(stage, "Persona eliminada correctamente");
			}
			else {
				mostrarAlertInfo(stage, "Error debe seleccionar una persona para eliminar");
			}
		});
		ObservableList<Persona> lista = tabla.getItems();
		filtroFld.setOnKeyTyped(e -> filtrar(filtroFld,tabla,lista));
		
		importBtn.setOnAction(e -> importar(stage));
		exportBtn.setOnAction(e -> exportar(tabla));
		
		Image image = new Image(getClass().getResource("/picture/agenda.png").toString());
		stage.getIcons().add(image);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("PERSONAS");
		stage.show();
	}

	

	private void exportar(TableView<Persona> tabla) {
		FileChooser elegir = new FileChooser();
		elegir.setTitle("Exportar CSV");
		elegir.setInitialDirectory(new File("/home/dm2/Escritorio/Irune"));
		elegir.setInitialFileName("nuevo.csv");
	}

	private void importar(Stage stage) {
		FileChooser elegir = new FileChooser();
		elegir.setTitle("Importar CSV");
		elegir.setInitialDirectory(new File("/home/dm2/Escritorio/Irune"));
		elegir.getExtensionFilters().addAll(new ExtensionFilter("Archivos HTML", "*.htm", "*.html"), new ExtensionFilter("Archivos CSV","*.csv"), new ExtensionFilter("Archivos de texto", "*.txt"),new ExtensionFilter("Todos los archivos", "*.*"));
		elegir.setSelectedExtensionFilter(elegir.getExtensionFilters().get(1));
		File file = elegir.showOpenDialog(stage);
		if (file == null) {
            return;
        }
		else {
			//BufferedReader fichero = new BufferedReader(file);
		}
	}

	private void filtrar(TextField filtroFld,TableView<Persona> tabla,ObservableList<Persona> lista) {
		
		if(!filtroFld.getText().isBlank()) {
			ObservableList<Persona> lista2 =  FXCollections.observableArrayList();
			for(int i = 0;i<lista.size();i++) {
				if(lista.get(i).getNombre().contains(filtroFld.getText())) {
					lista2.add(lista.get(i));
				}
			}
			tabla.setItems(lista2);
			
		}
		else {
			tabla.setItems(lista);
		}
	}

	private void abrirVentana(TableView<Persona> tabla,int parametro) {
		TextField fNameField = new TextField();
        TextField lNameField = new TextField();
        TextField edadFld = new TextField();
        
        Label nombreLbl =  new Label("Nombre:");
        Label apellidoLbl = new Label("Apellido:");
        Label edadLbl = new Label("Edad:");
        nombreLbl.setAlignment(Pos.CENTER_RIGHT);
        apellidoLbl.setAlignment(Pos.CENTER_RIGHT);
        edadLbl.setAlignment(Pos.CENTER_RIGHT);
        edadFld.setPrefColumnCount(10);
        GridPane root = new GridPane();
        
        Button guardar = new Button("Guardar");
        Button cancelar = new Button("Cancelar");
        guardar.setStyle("-fx-margin: 0 20 0 0;");
        cancelar.setStyle("-fx-margin: 0 20 0 0;");
        
        root.setHgap(10);
        root.setVgap(5);
        root.addRow(0,nombreLbl, fNameField);
        root.addRow(1, apellidoLbl, lNameField);
        root.addRow(2, edadLbl, edadFld);
       
        root.addRow(3,guardar,cancelar);
        root.setStyle("-fx-padding: 20;");
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.setTitle("Nueva persona");
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
        newStage.setResizable(false);
        
         if(parametro==2) {
			if(tabla.getSelectionModel().getSelectedItem()!=null) {
				Persona p = tabla.getSelectionModel().getSelectedItem();
				fNameField.setText(p.getNombre());
				lNameField.setText(p.getApellidos());
				edadFld.setText(p.getEdad()+""); 
			}
			else {
				 mostrarAlertInfo(newStage,"Error debes seleccionar una persona");
				 newStage.close();
			}
        }
        
        guardar.setOnAction(e -> guardar(newStage,fNameField,lNameField,edadFld,tabla,parametro));
        
        cancelar.setOnAction(e -> newStage.close());
        
        
	}


	private void guardar(Stage newStage,TextField fNameField,TextField lNameField,TextField edadFld,TableView<Persona> tabla,int parametro) {
		{
        	String str = comprobar(fNameField.getText(),lNameField.getText(),edadFld.getText());
			 if(str.equals("")) {
				 if(parametro==1) { //guardamos persona nueva
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
				 else {  //modificamos persona
					 Persona p = tabla.getSelectionModel().getSelectedItem();
					 Persona p2 = new Persona(fNameField.getText(),lNameField.getText(),Integer.parseInt(edadFld.getText()));
						 if(str.equals("")) {
							 if(!estaRepe(p2,tabla)) {
								p.setNombre(fNameField.getText());
								p.setApellidos(lNameField.getText());
								p.setEdad(Integer.parseInt(edadFld.getText()));
								mostrarAlertInfo(newStage,"Persona modificada correctamente");
								limpiar(fNameField,lNameField,edadFld);
								 newStage.close();
								tabla.refresh();
							 }
							 else {
								 mostrarAlertInfo(newStage,"Error La persona ya está en la tabla");
							 }
						}
						 else {
							 mostrarAlertInfo(newStage,str);
						 }
				 }
			 }
			 else {
				 mostrarAlertInfo(newStage,str);
			 
			 }
        }
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
