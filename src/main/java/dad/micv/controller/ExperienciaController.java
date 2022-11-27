package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ExperienciaController implements Initializable {
	@FXML
	private Button anadirExpBoton;

	@FXML
	private TableColumn<?, ?> denominacionColumna;

	@FXML
	private TableColumn<?, ?> desdeColumna;

	@FXML
	private Button eliminarExpBoton;

	@FXML
	private TableColumn<?, ?> empleadorColumna;

	@FXML
	private TableView<?> experienciaTabla;

	@FXML
	private TableColumn<?, ?> hastaColumna;

	@FXML
	private BorderPane view;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	public ExperienciaController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
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
