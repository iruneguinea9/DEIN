package application;
	
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import util.Propiedades;
import util.Utilidades;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			ResourceBundle bundle = ResourceBundle.getBundle("idiomas/messages");
			
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/ventanaPrincipal.fxml"),bundle);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setTitle(bundle.getString("label1"));
			primaryStage.setScene(scene);
			Image image = new Image(getClass().getResource("/images/olimpiadas.png").toString());
			primaryStage.getIcons().add(image);
			primaryStage.show();
		} catch(Exception e) {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			ResourceBundle bundle = ResourceBundle.getBundle("idiomas/messages");
			Utilidades.mostrarAlertInfo(primaryStage,bundle.getString("err1") );
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

