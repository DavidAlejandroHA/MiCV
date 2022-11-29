package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogos.ExperienciaDialog;
import dad.micv.model.Experiencia;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
	private TableColumn<Experiencia, String> denominacionColumna;

	@FXML
	private TableColumn<Experiencia, LocalDate> desdeColumna;

	@FXML
	private Button eliminarExpBoton;

	@FXML
	private TableColumn<Experiencia, String> empleadorColumna;

	@FXML
	private TableView<Experiencia> experienciaTabla;

	@FXML
	private TableColumn<Experiencia, LocalDate> hastaColumna;

	@FXML
	private BorderPane view;

	private ListProperty<Experiencia> experiencias = new SimpleListProperty<>(FXCollections.observableArrayList());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		experienciaTabla.itemsProperty().bindBidirectional(experiencias);
		desdeColumna.setCellValueFactory(valor -> valor.getValue().desdeProperty());
		hastaColumna.setCellValueFactory(valor -> valor.getValue().hastaProperty());
		denominacionColumna.setCellValueFactory(valor -> valor.getValue().denominacionProperty());
		empleadorColumna.setCellValueFactory(valor -> valor.getValue().empleadorProperty());
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

	@FXML
	void onAnadirExperiencia(ActionEvent event) {
		ExperienciaDialog dialogo = new ExperienciaDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Experiencia> valores = dialogo.showAndWait();
		if (valores.isPresent()) { // El Optional permite comprobar si todos los valores están presentes
			//System.out.println(valores.get().getDenominacion().toString());
			experiencias.add(valores.get());
		}
	}

	@FXML
	void onEliminarExperiencia(ActionEvent event) {
		experiencias.remove(experienciaTabla.getSelectionModel().selectedIndexProperty().get());
	}

	public final ListProperty<Experiencia> experienciasProperty() {
		return this.experiencias;
	}
	

	public final ObservableList<Experiencia> getExperiencias() {
		return this.experienciasProperty().get();
	}
	

	public final void setExperiencias(final ObservableList<Experiencia> experiencias) {
		this.experienciasProperty().set(experiencias);
		experienciaTabla.itemsProperty().set(experiencias);
	}
	
}
