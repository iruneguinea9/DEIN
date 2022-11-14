package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FlowPane root = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/ventanaPrincipal.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setTitle("Productos");
			primaryStage.setScene(scene);
			Image image = new Image(getClass().getResource("/images/carrito.png").toString());
			primaryStage.getIcons().add(image);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			//mostrarAlertInfo(primaryStage,"Error al abrir la ventana" );
		}
	}
	public static void main(String[] args) {
		launch(args);
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
}