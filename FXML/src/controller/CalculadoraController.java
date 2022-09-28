package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import model.Operaciones;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;

public class CalculadoraController implements Initializable{
	@FXML
	private TextField txtOperador1;
	@FXML
	private TextField txtOperador2;
	@FXML
	private Button btncalcular;
	@FXML
	private RadioButton btnsumar;
	@FXML
	private ToggleGroup operaciones;
	@FXML
	private RadioButton btnrestar;
	@FXML
	private RadioButton btnmulti;
	@FXML
	private RadioButton btndividir;
	@FXML
	private TextField txtResultado;

	// Event Listener on Button[#btncalcular].onAction
	@FXML
    public void calcular(ActionEvent event) {
        try {
            double op1 = Double.parseDouble(this.txtOperador1.getText());
            double op2 = Double.parseDouble(this.txtOperador2.getText());
            Operaciones operaciones = new Operaciones(op1, op2);
            if (this.btnsumar.isSelected()) {
                this.txtResultado.setText(operaciones.sumar() + "");
            } else if (this.btnrestar.isSelected()) {
                this.txtResultado.setText(operaciones.restar() + "");
            } else if (this.btnmulti.isSelected()) {
                this.txtResultado.setText(operaciones.multiplicar() + "");
            } else if (this.btndividir.isSelected()) {
                if (op2 == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("El operando 2 no puede ser 0");
                    alert.showAndWait();
                } else {
                    this.txtResultado.setText(operaciones.dividir() + "");
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato incorrecto");
            alert.showAndWait();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}

