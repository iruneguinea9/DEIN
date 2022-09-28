module tanda1 {
//	requires javafx.controls;
//	requires javafx.web;
//	requires javafx.fxml;
//	requires javafx.swing;
//	requires javafx.media;
//	requires javafx.graphics;
//	requires javafx.base;
	
	
	requires javafx.controls;
	requires java.desktop;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires jdk.jsobject;
	requires javafx.graphics;
	requires javafx.base;

	opens tanda1 to javafx.graphics, javafx.base;
	opens model to javafx.graphics, javafx.base;
}