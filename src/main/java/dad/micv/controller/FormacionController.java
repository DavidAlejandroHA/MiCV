package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogos.TituloDialog;
import dad.micv.model.Titulo;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class FormacionController implements Initializable {
	@FXML
	private Button anadirFormacionBoton;

	@FXML
	private TableColumn<Titulo, String> denominacionColumna;

	@FXML
	private TableColumn<Titulo, LocalDate> desdeColumna;

	@FXML
	private Button eliminarFormacionBoton;

	@FXML
	private TableView<Titulo> formacionTabla;

	@FXML
	private TableColumn<Titulo, LocalDate> hastaColumna;

	@FXML
	private TableColumn<Titulo, String> organizadorColumna;

	@FXML
	private BorderPane view;

	private ListProperty<Titulo> formacion = new SimpleListProperty<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		formacionTabla.itemsProperty().bindBidirectional(formacion);
		desdeColumna.setCellValueFactory(valor -> valor.getValue().desdeProperty());
		hastaColumna.setCellValueFactory(valor -> valor.getValue().hastaProperty());
		denominacionColumna.setCellValueFactory(valor -> valor.getValue().denominacionProperty());
		organizadorColumna.setCellValueFactory(valor -> valor.getValue().organizadorProperty());

	}

	public FormacionController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onAnadirFormacion(ActionEvent event) {
		TituloDialog dialogo = new TituloDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Titulo> valores = dialogo.showAndWait();
		if (valores.isPresent()) { // El Optional permite comprobar si todos los valores están presentes
			formacion.add(valores.get());
		}
	}

	@FXML
	void onEliminarFormacion(ActionEvent event) {

	}

	public Button getAnadirFormacionBoton() {
		return anadirFormacionBoton;
	}

	public TableColumn<Titulo, String> getDenominacionColumna() {
		return denominacionColumna;
	}

	public TableColumn<Titulo, LocalDate> getDesdeColumna() {
		return desdeColumna;
	}

	public Button getEliminarFormacionBoton() {
		return eliminarFormacionBoton;
	}

	public TableView<Titulo> getFormacionTabla() {
		return formacionTabla;
	}

	public TableColumn<Titulo, LocalDate> getHastaColumna() {
		return hastaColumna;
	}

	public TableColumn<Titulo, String> getOrganizadorColumna() {
		return organizadorColumna;
	}

	public BorderPane getView() {
		return view;
	}
}
