module tanda2 {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires javafx.graphics;
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml;
	opens model to javafx.graphics, javafx.fxml;
}
