package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ConocimientosController implements Initializable {

	@FXML
	private Button anadirConBoton;

	@FXML
	private Button anadirIdiomaBoton;

	@FXML
	private TableView<?> conocimientosTabla;

	@FXML
	private TableColumn<?, ?> denominacionColumna;

	@FXML
	private Button eliminarConBoton;

	@FXML
	private TableColumn<?, ?> nivelColumna;

	@FXML
	private BorderPane view;

	@FXML
	void onAnadirConocimiento(ActionEvent event) {

	}

	@FXML
	void onAnadirIdioma(ActionEvent event) {

	}

	@FXML
	void onEliminarAction(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public ConocimientosController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BorderPane getView() {
		return view;
	}
}
