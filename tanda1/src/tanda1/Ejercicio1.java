//Tanda 1 ejercicio 1
package tanda1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Ejercicio1 extends Application{
	private CheckBox deporteCheck;
	private ComboBox<String> edades;
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		
		Node right = null;
		Node left = null;
		
		
		HBox titulo = new HBox();
		titulo.setAlignment(Pos.CENTER);
		Label encuestaTitLbl = new Label("ENCUESTA");
		titulo.getChildren().add(encuestaTitLbl);
		titulo.setStyle(
                "-fx-font-size: 25pt;" +
                "-fx-padding: 10 0 0 0;" +"-fx-font-weight: bolder;");
		
		// Profesion
				Label profLbl = new Label("Profesion:");
				TextField profFld = new TextField();
				HBox profesion = new HBox(profLbl, profFld);
				profesion.setHgrow(profFld, Priority.ALWAYS);
				profesion.setSpacing(10);
						
				// Numero hermanos
				Label hermanosLbl = new Label("Nº Hermanos: ");
				TextField hermanosFld = new TextField();
				HBox hermanos = new HBox(hermanosLbl, hermanosFld);	
						
				// Edad
				Label edadLbl = new Label("Edad: ");
				ObservableList<String> edadLst = FXCollections.<String>observableArrayList( "Menores de 18", "Entre 18 y 30", "Entre 31 y 50", "Entre 51 y 70", "Mayores de 70"); 
				edades = new ComboBox<String>(edadLst);
				HBox edad = new HBox(edadLbl, edades);	
				
				
						
				SplitPane sp = new SplitPane();
				sp.getItems().addAll(hermanos,edad);
				HBox sexBox = new HBox();
				
				Label sexoLbl = new Label("Sexo: ");
			
				RadioButton rb1 = new RadioButton("Hombre");
		        RadioButton rb2 = new RadioButton("Mujer");
		        RadioButton rb3 = new RadioButton("Otro");
		        
		        ToggleGroup group = new ToggleGroup();
				rb1.setToggleGroup(group);
				rb2.setToggleGroup(group);
				rb3.setToggleGroup(group);
		        rb2.setSelected(true);
		        
		        sexBox.getChildren().addAll(sexoLbl,rb1,rb2,rb3);
		        sexBox.setSpacing(20);
		        sexBox.setHgrow(sexoLbl, Priority.ALWAYS);
		        //Deportes
		        
		        deporteCheck = new CheckBox("¿Practica algún deporte?");
		        deporteCheck.setPrefWidth(400);
		        deporteCheck.setAlignment(Pos.TOP_LEFT);
		        
		        
		        // cuales
		        Label cualLbl = new Label("¿Cual?");
		        
		        ObservableList<String> listaDeportes = FXCollections.observableArrayList("Tenis","Fútbol","Baloncesto","Natación","Ciclismo","Otro");
		      
		        ListView<String> listView = new ListView<String>(listaDeportes);
		        listView.setPrefSize(250, 200);
		        VBox cuales = new VBox(cualLbl,listView);
		        cuales.setVgrow(cuales, Priority.ALWAYS);
		        listView.setDisable(true);
		        deporteCheck.setOnAction(e -> {
		        	listView.setDisable(false);
		        });
		        
		    	SplitPane sp2 = new SplitPane();
				sp2.getItems().addAll(deporteCheck,cuales);
		        
		        
				VBox center = new VBox(profesion, sp,sexBox,sp2);
				center.setStyle( "-fx-padding: 10 0 0 0;" );
				center.setVgrow(center,Priority.ALWAYS);
		
		
		
		VBox bottom = new VBox(10);
		Label encuestaLbl = new Label("Marque del 1 al 10 su grado de afición");
		encuestaLbl.setStyle(
                "-fx-font-size: 10pt;" +
                "-fx-padding: 10 0 0 0;" +"-fx-font-weight: bolder;");
		//Compras
		
		HBox comprasBox = new HBox();
		Label comprasLbl = new Label("Compras: ");
		Slider s1 = new Slider(0,10,5);
		comprasBox.getChildren().addAll(comprasLbl,s1);
		s1.setTooltip(new Tooltip("indica de 1 a 10 cuanto te gusta ir de compras"));
		comprasBox.setHgrow(s1, Priority.ALWAYS);
		s1.setShowTickMarks(true);
		s1.setShowTickLabels(true);
		s1.setMajorTickUnit(1);
		s1.setBlockIncrement(1);
		
		
		//Ver television
		HBox teleBox = new HBox();
		Label teleLbl = new Label("Ver televisión: ");
		Slider s2 = new Slider(0,10,5);
		teleBox.getChildren().addAll(teleLbl,s2);
		s2.setTooltip(new Tooltip("indica de 1 a 10 cuanto te gusta ver la television"));
		teleBox.setHgrow(s2, Priority.ALWAYS);
		s2.setShowTickMarks(true);
		s2.setShowTickLabels(true);
		s2.setMajorTickUnit(1);
		s2.setBlockIncrement(1);
		
		// ir al cine
		HBox cineBox = new HBox();
		Label cineLbl = new Label("Ir al cine: ");
		Slider s3 = new Slider(0,10,5);
		cineBox.getChildren().addAll(cineLbl,s3);
		s1.setTooltip(new Tooltip("indica de 1 a 10 cuanto te gusta ir al cine"));
		cineBox.setHgrow(s3, Priority.ALWAYS);
		s3.setShowTickMarks(true);
		s3.setShowTickLabels(true);
		s3.setMajorTickUnit(1);
		s3.setBlockIncrement(1);
		
		
		//botones 
		HBox botones = new HBox();
		Button aceptarBtn = new Button("Aceptar");
		Button cancelarBtn = new Button("Cancelar");
		botones.getChildren().addAll(aceptarBtn,cancelarBtn);
		aceptarBtn.setTooltip(new Tooltip("Aceptar y guardar"));
		cancelarBtn.setTooltip(new Tooltip("Cancelar"));
		botones.setAlignment(Pos.BASELINE_CENTER);
		
		//Eventos botones
		//cancelarBtn.setOnAction();
		aceptarBtn.setOnAction(e -> {
			String str = comprobar(profFld.getText(),hermanosFld.getText(),edades.getValue(),deporteCheck ,listView);
			if(str.equals("")) {
				//está bien
				mostrarAlertInfo(stage, profFld.getText(),hermanosFld.getText(),edades.getValue(),deporteCheck ,listView, s1, s2, s3);
			}
			else {
				//tiene errores
				mostrarAlertError(stage, str);
			}
		});
		cancelarBtn.setOnAction(e -> stage.close());
		
		bottom.getChildren().addAll(encuestaLbl,comprasBox,teleBox,cineBox,botones);
		bottom.setAlignment(Pos.CENTER);
		
		BorderPane root = new BorderPane(center, titulo, right, bottom, left);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("ENCUESTA");
		stage.show();
	}

	private String comprobar(String profesion,String hermanos,String edades,CheckBox check,ListView<String> lista) {
		String str="";
		try {
			if(profesion.isBlank())
				str += "Debe introducir una profesion\n";
			if(hermanos.isBlank())
				str += "Debe introducir un numero de hermanos\n";
			try {
				Integer.parseInt(hermanos);
			}catch(NumberFormatException e1) {
				str += "Debe introducir un numero de hermanos correcto\n";
			}
			if(check.isPressed()) {
				if(lista.getItems().isEmpty())
					str += "Debe introducir deporte(s)\n";
			}
			edades.isBlank();
			
		}catch(NullPointerException e) {
			str += "Debe introducir una edad\n";
			return str;
		}
		return str;
	}
	private void mostrarAlertError(Window win,String str) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initOwner(win);
		alert.setHeaderText(null);
		alert.setTitle("TUS DATOS");
		alert.setContentText(str);
		alert.showAndWait();
	}
	
	private void mostrarAlertInfo(Window win,String profesion,String hermanos,String edades,CheckBox check,ListView<String> lista,Slider s1,Slider s2,Slider s3) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initOwner(win);
		alert.setHeaderText(null);
		alert.setTitle("TUS DATOS");
		String str ="Profesion: "+profesion+"\nNumero de hermanos: "+hermanos+"\nEdad: "+edades;
		if(check.isPressed()) {
			str+= "\nDeportes: "+lista.getItems().toString()+"\n";
		}
		str += "\nGrado de aficion a las compras: "+s1.getValue()+"\nGrado de aficion a ver la television: "+s2.getValue()+"\nGrado de aficion a ir al cine: "+s3.getValue();
		alert.setContentText(str);
		alert.showAndWait();
	}

}
