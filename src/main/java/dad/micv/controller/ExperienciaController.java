package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogos.ExperienciaDialog;
import dad.micv.model.Experiencia;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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

	// model

	private ListProperty<Experiencia> experiencias = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<ListProperty<Experiencia>> experienciasObjectProp = new SimpleObjectProperty<>(experiencias);

	// view
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		experienciaTabla.itemsProperty().bindBidirectional(experiencias);
		desdeColumna.setCellValueFactory(valor -> valor.getValue().desdeProperty());
		hastaColumna.setCellValueFactory(valor -> valor.getValue().hastaProperty());
		denominacionColumna.setCellValueFactory(valor -> valor.getValue().denominacionProperty());
		empleadorColumna.setCellValueFactory(valor -> valor.getValue().empleadorProperty());

		eliminarExpBoton.disableProperty()
				.bind(Bindings.when(experienciaTabla.getSelectionModel().selectedItemProperty().isNull()).then(true)
						.otherwise(false));

		// listeners

		experienciasObjectProp.addListener(this::onExperienciaChanged);
	}

	public void onExperienciaChanged(ObservableValue<? extends ListProperty<Experiencia>> o,
			ListProperty<Experiencia> ov, ListProperty<Experiencia> nv) {
		if (ov != null) {
			experienciaTabla.itemsProperty().bind(ov);
		}

		if (nv != null) {
			experienciaTabla.itemsProperty().bind(nv);
		}

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
		if (valores.isPresent()) { // El isPresent del Optional permite comprobar si el valor retornado es distinto de nulo
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
	}

}
